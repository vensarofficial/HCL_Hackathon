package com.example.payment.web;

import com.example.payment.dto.ConfirmPaymentRequest;
import com.example.payment.dto.PaymentResponse;
import com.example.payment.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

  private final PaymentService service;
  public PaymentController(PaymentService service) { this.service = service; }

  @PostMapping("/confirm")
  public PaymentResponse confirm(@Valid @RequestBody ConfirmPaymentRequest req,
                                 @RequestHeader(value="Idempotency-Key", required=false) String idemHeader) {
    String key = (idemHeader != null && !idemHeader.isBlank()) ? idemHeader : req.idempotencyKey;
    return service.confirm(req.draftId, key);
  }
}
