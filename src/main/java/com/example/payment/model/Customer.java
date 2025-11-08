package com.example.payment.model;

import jakarta.persistence.*;
import java.time.OffsetDateTime;

@Entity @Table(name="customers")
public class Customer {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(nullable=false, unique=true) private String email;
  @Column(nullable=false) private String fullName;
  @Column(nullable=false) private OffsetDateTime createdAt = OffsetDateTime.now();

  public Long getId() { return id; }
  public String getEmail() { return email; }
  public void setEmail(String email) { this.email = email; }
  public String getFullName() { return fullName; }
  public void setFullName(String fullName) { this.fullName = fullName; }
  public OffsetDateTime getCreatedAt() { return createdAt; }
}
