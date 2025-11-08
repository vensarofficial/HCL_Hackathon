package com.example.payment.dto;

import java.math.BigDecimal;

public class PaymentResponse {
  public Long transactionId;
  public Long settlementId;
  public String status;
  public BigDecimal amount;
  public BigDecimal fee;
  public BigDecimal netAmount;
}
