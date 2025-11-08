package com.example.payment.web;

import com.example.payment.dto.CreateDraftRequest;
import com.example.payment.dto.DraftResponse;
import com.example.payment.model.*;
import com.example.payment.repo.*;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/drafts")
public class DraftController {

  private final PurchaseDraftRepo draftRepo;
  private final CustomerRepo customerRepo;
  private final ProductRepo productRepo;

  public DraftController(PurchaseDraftRepo draftRepo, CustomerRepo customerRepo, ProductRepo productRepo) {
    this.draftRepo = draftRepo;
    this.customerRepo = customerRepo;
    this.productRepo = productRepo;
  }

  @PostMapping
  public DraftResponse create(@Valid @RequestBody CreateDraftRequest req) {
    Customer c = customerRepo.findById(req.customerId).orElseThrow();
    Product p = productRepo.findById(req.productId).orElseThrow();
    PurchaseDraft d = new PurchaseDraft();
    d.setCustomer(c);
    d.setProduct(p);
    d.setQuantity(req.quantity);
    d.setCurrency(Currency.valueOf(req.currency));
    d.setStatus("INITIATED");
    draftRepo.save(d);
    return new DraftResponse(d.getId(), d.getStatus());
  }
}
