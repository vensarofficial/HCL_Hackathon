package com.example.payment.repo;
import com.example.payment.model.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;
public interface MerchantRepo extends JpaRepository<Merchant, Long> { }
