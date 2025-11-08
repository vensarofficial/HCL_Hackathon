package com.hcl.wallet.mapper;

import com.hcl.wallet.dto.WalletSummaryDTO;
import com.hcl.wallet.entity.Wallet;
import org.springframework.stereotype.Component;

@Component
public class WalletMapper {

    public WalletSummaryDTO toDto(Wallet wallet) {
        return WalletSummaryDTO.builder()
                .walletId(wallet.getWalletId())
                .customerId(wallet.getCustomer().getCustomerId())
                .customerName(wallet.getCustomer().getFullName())
                .currency(wallet.getCurrency())
                .balance(wallet.getBalance())
                .status(wallet.getStatus().name())
                .build();
    }
}
