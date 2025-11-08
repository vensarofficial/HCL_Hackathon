package com.hcl.wallet.service.impl;


import com.hcl.wallet.dto.WalletSummaryDTO;
import com.hcl.wallet.entity.Wallet;
import com.hcl.wallet.exception.InsufficientBalanceException;
import com.hcl.wallet.exception.ResourceNotFoundException;
import com.hcl.wallet.mapper.WalletMapper;
import com.hcl.wallet.repository.WalletRepository;
import com.hcl.wallet.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;
    private final WalletMapper walletMapper;

    @Override
    public WalletSummaryDTO getWalletSummary(String customerId) {
        Wallet wallet = walletRepository.findByCustomer_CustomerId(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Wallet not found for customer: " + customerId));
        return walletMapper.toDto(wallet);
    }

    @Override
    public boolean validateSufficientBalance(String customerId, double amount) {
        Wallet wallet = walletRepository.findByCustomer_CustomerId(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Wallet not found"));
        return wallet.getBalance().compareTo(BigDecimal.valueOf(amount)) >= 0;
    }

    @Override
    @Transactional
    public void deductBalance(String customerId, double amount) {
        Wallet wallet = walletRepository.findByCustomer_CustomerId(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Wallet not found"));

        if (wallet.getBalance().compareTo(BigDecimal.valueOf(amount)) < 0) {
            throw new InsufficientBalanceException("Insufficient wallet balance");
        }

        wallet.setBalance(wallet.getBalance().subtract(BigDecimal.valueOf(amount)));
        wallet.setLastUpdated(LocalDateTime.now());
        walletRepository.save(wallet);
    }

    @Override
    @Transactional
    public void creditBalance(String customerId, double amount) {
        Wallet wallet = walletRepository.findByCustomer_CustomerId(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Wallet not found"));

        wallet.setBalance(wallet.getBalance().add(BigDecimal.valueOf(amount)));
        wallet.setLastUpdated(LocalDateTime.now());
        walletRepository.save(wallet);
    }
}

