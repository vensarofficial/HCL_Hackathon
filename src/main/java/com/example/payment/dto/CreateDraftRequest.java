package com.example.payment.dto;

import jakarta.validation.constraints.*;

public class CreateDraftRequest {
  @NotNull public Long customerId;
  @NotNull public Long productId;
  @Min(1) public int quantity = 1;
  @NotBlank public String currency; // e.g., INR
}
