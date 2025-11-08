package com.example.payment.service;

import com.example.payment.dto.PaymentResponse;
import com.example.payment.model.*;
import com.example.payment.repo.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class PaymentService {

    private final PurchaseDraftRepo draftRepo;
    private final WalletCustomRepo walletRepo;
    private final ProductRepo productRepo;
    private final MerchantRepo merchantRepo;
    private final TxnRepo txnRepo;
    private final LedgerRepo ledgerRepo;
    private final SettlementRepo settlementRepo;
    private final IdemKeyRepo idemKeyRepo;
    private final NotificationRepo notificationRepo;

    private final ObjectMapper mapper;

    public PaymentService(PurchaseDraftRepo draftRepo,
                          WalletCustomRepo walletRepo,
                          ProductRepo productRepo,
                          MerchantRepo merchantRepo,
                          TxnRepo txnRepo,
                          LedgerRepo ledgerRepo,
                          SettlementRepo settlementRepo,
                          IdemKeyRepo idemKeyRepo,
                          NotificationRepo notificationRepo,
                          ObjectMapper mapper) {

        this.draftRepo = draftRepo;
        this.walletRepo = walletRepo;
        this.productRepo = productRepo;
        this.merchantRepo = merchantRepo;
        this.txnRepo = txnRepo;
        this.ledgerRepo = ledgerRepo;
        this.settlementRepo = settlementRepo;
        this.idemKeyRepo = idemKeyRepo;
        this.notificationRepo = notificationRepo;
        this.mapper = mapper;
    }

    @Transactional
    public PaymentResponse confirm(Long draftId, String idemKey) {

        // 1) Idempotency Check
        Optional<IdempotencyKey> existing = idemKeyRepo.findByIdempotencyKey(idemKey);

        if (existing.isPresent()) {
            IdempotencyKey key = existing.get();

            if ("COMPLETED".equals(key.getStatus()) && key.getResponseSnapshot() != null) {
                try {
                    return mapper.readValue(key.getResponseSnapshot(), PaymentResponse.class);
                } catch (Exception e) {
                    throw new IllegalStateException("Stored idempotent response unreadable", e);
                }
            }

            if ("IN_PROGRESS".equals(key.getStatus())) {
                throw new IllegalStateException("Duplicate request in progress");
            }
        } else {
            // Create new IN_PROGRESS
            IdempotencyKey k = new IdempotencyKey();
            k.setIdempotencyKey(idemKey);
            k.setStatus("IN_PROGRESS");
            idemKeyRepo.save(k);
        }

        // 2) Business Logic
        PurchaseDraft draft = draftRepo.findById(draftId)
                .orElseThrow(() -> new IllegalArgumentException("Draft not found"));

        Product product = productRepo.findWithLockById(draft.getProduct().getId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        if (!product.getActive()) throw new IllegalStateException("Product inactive");

        Wallet wallet = walletRepo
                .lockByCustomerAndCurrency(draft.getCustomer().getId(), draft.getCurrency())
                .orElseThrow(() -> new IllegalStateException("Wallet not found/locked"));

        if (product.getStock() < draft.getQuantity())
            throw new IllegalStateException("Insufficient stock");

        BigDecimal amount = product.getPrice().multiply(BigDecimal.valueOf(draft.getQuantity()));
        BigDecimal fee = amount.multiply(new BigDecimal("0.02")).setScale(2, BigDecimal.ROUND_HALF_UP);
        BigDecimal total = amount.add(fee);

        if (wallet.getBalance().compareTo(total) < 0)
            throw new IllegalStateException("Insufficient wallet balance");

        wallet.setBalance(wallet.getBalance().subtract(total));
        product.setStock(product.getStock() - draft.getQuantity());

        Txn txn = new Txn();
        txn.setWallet(wallet);
        txn.setMerchant(product.getMerchant());
        txn.setProduct(product);
        txn.setQuantity(draft.getQuantity());
        txn.setAmount(amount);
        txn.setFee(fee);
        txn.setCurrency(product.getCurrency());
        txn.setStatus("PAID");
        txnRepo.save(txn);

        LedgerEntry le1 = new LedgerEntry();
        le1.setTransaction(txn);
        le1.setEntryType("DEBIT_WALLET");
        le1.setAmount(total);
        ledgerRepo.save(le1);

        LedgerEntry le2 = new LedgerEntry();
        le2.setTransaction(txn);
        le2.setEntryType("FEE");
        le2.setAmount(fee);
        ledgerRepo.save(le2);

        LedgerEntry le3 = new LedgerEntry();
        le3.setTransaction(txn);
        le3.setEntryType("CREDIT_MERCHANT");
        le3.setAmount(amount);
        ledgerRepo.save(le3);

        Settlement st = new Settlement();
        st.setTransaction(txn);
        st.setMerchant(product.getMerchant());
        st.setGrossAmount(amount);
        st.setFee(fee);
        st.setNetAmount(amount.subtract(fee));
        st.setStatus("INITIATED");
        settlementRepo.save(st);

        Notification n = new Notification();
        n.setTransaction(txn);
        n.setMerchant(product.getMerchant());
        n.setChannel("WEBHOOK");
        n.setPayload("{\"event\":\"payment.paid\",\"transactionId\":"+txn.getId()+"}");
        notificationRepo.save(n);

        PaymentResponse resp = new PaymentResponse();
        resp.transactionId = txn.getId();
        resp.settlementId = st.getId();
        resp.status = "SUCCESS";
        resp.amount = amount;
        resp.fee = fee;
        resp.netAmount = st.getNetAmount();

        // 3) Save Final Idempotent State
        IdempotencyKey done = idemKeyRepo.findByIdempotencyKey(idemKey).orElseThrow();

        try {
            String json = mapper.writeValueAsString(resp);
            done.setResponseSnapshot(json);
        } catch (Exception e) {
            throw new IllegalStateException("Could not serialize idempotent response", e);
        }

        done.setStatus("COMPLETED");
        idemKeyRepo.save(done);  // CRITICAL to ensure UPDATE happens

        // 4) Return response
        return resp;
    }
}
