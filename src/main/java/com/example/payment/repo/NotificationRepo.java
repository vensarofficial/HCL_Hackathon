package com.example.payment.repo;
import com.example.payment.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
public interface NotificationRepo extends JpaRepository<Notification, Long> { }
