package com.example.payment.repo;
import com.example.payment.model.PurchaseDraft;
import org.springframework.data.jpa.repository.JpaRepository;
public interface PurchaseDraftRepo extends JpaRepository<PurchaseDraft, Long> { }
