package com.hcl.wallet.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WalletSummaryDTO {
    private String walletId;
    private String customerId;
    private String customerName;
    private String currency;
    private BigDecimal balance;
    private String status;
}