package com.example.payment.dto;

import jakarta.validation.constraints.*;

public class ConfirmPaymentRequest {
  @NotNull public Long draftId;
  @NotBlank public String idempotencyKey;
}
