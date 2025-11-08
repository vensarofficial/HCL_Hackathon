package com.example.payment.repo;
import com.example.payment.model.LedgerEntry;
import org.springframework.data.jpa.repository.JpaRepository;
public interface LedgerRepo extends JpaRepository<LedgerEntry, Long> { }
