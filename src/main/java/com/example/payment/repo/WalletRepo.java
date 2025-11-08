package com.example.payment.repo;

import com.example.payment.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.QueryHints;
import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;

import java.util.Optional;

public interface WalletRepo extends JpaRepository<Wallet, Long> {
  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @QueryHints(@QueryHint(name = "jakarta.persistence.lock.timeout", value = "5000"))
  Optional<Wallet> findById(Long id);
}
