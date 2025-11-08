package com.example.payment.repo;
import com.example.payment.model.Settlement;
import org.springframework.data.jpa.repository.JpaRepository;
public interface SettlementRepo extends JpaRepository<Settlement, Long> { }
