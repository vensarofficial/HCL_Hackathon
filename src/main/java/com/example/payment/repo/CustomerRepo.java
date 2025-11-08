package com.example.payment.repo;
import com.example.payment.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface CustomerRepo extends JpaRepository<Customer, Long> {
  Optional<Customer> findByEmail(String email);
}
