package com.example.payment.repo;
import com.example.payment.model.IdempotencyKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IdemKeyRepo extends JpaRepository<IdempotencyKey, Long> {
  Optional<IdempotencyKey> findByIdempotencyKey(String key);
}
