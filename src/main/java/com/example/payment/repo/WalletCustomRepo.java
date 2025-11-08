package com.example.payment.repo;

import com.example.payment.model.Wallet;
import com.example.payment.model.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.persistence.LockModeType;
import java.util.Optional;

public interface WalletCustomRepo extends JpaRepository<Wallet, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
            SELECT w FROM Wallet w
            WHERE w.customer.id = :customerId
              AND w.currency = :currency
            """)
    Optional<Wallet> lockByCustomerAndCurrency(
            @Param("customerId") Long customerId,
            @Param("currency") Currency currency
    );
}
