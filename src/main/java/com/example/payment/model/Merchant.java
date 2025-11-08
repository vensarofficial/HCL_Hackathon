package com.example.payment.model;

import jakarta.persistence.*;
import java.time.OffsetDateTime;

@Entity @Table(name="merchants")
public class Merchant {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(nullable=false) private String name;
  private String email;
  @Column(nullable=false) private OffsetDateTime createdAt = OffsetDateTime.now();

  public Long getId() { return id; }
  public String getName() { return name; }
  public void setName(String name) { this.name = name; }
  public String getEmail() { return email; }
  public void setEmail(String email) { this.email = email; }
  public OffsetDateTime getCreatedAt() { return createdAt; }
}
