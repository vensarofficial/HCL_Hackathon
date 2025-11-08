package com.example.payment.repo;
import com.example.payment.model.Txn;
import org.springframework.data.jpa.repository.JpaRepository;
public interface TxnRepo extends JpaRepository<Txn, Long> { }
