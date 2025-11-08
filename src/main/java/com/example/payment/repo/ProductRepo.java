package com.example.payment.repo;

import com.example.payment.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.QueryHints;
import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;

import java.util.Optional;

public interface ProductRepo extends JpaRepository<Product, Long> {
  Optional<Product> findBySku(String sku);

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @QueryHints(@QueryHint(name = "jakarta.persistence.lock.timeout", value = "5000"))
  Optional<Product> findWithLockById(Long id);
}
