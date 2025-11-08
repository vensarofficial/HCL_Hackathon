package com.hcl.wallet.service;

import com.hcl.wallet.dto.WalletSummaryDTO;

public interface WalletService {
    WalletSummaryDTO getWalletSummary(String customerId);
    boolean validateSufficientBalance(String customerId, double amount);
    void deductBalance(String customerId, double amount);
    void creditBalance(String customerId, double amount);
}
