package com.example.payment.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity @Table(name="wallets",
  uniqueConstraints = @UniqueConstraint(columnNames = {"customer_id","currency"}))
public class Wallet {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name="customer_id", nullable=false)
  private Customer customer;

  @Enumerated(EnumType.STRING) @Column(nullable=false)
  private Currency currency;

  @Column(nullable=false, precision=19, scale=2)
  private BigDecimal balance = BigDecimal.ZERO;

  @Column(nullable=false) private OffsetDateTime updatedAt = OffsetDateTime.now();

  public Long getId() { return id; }
  public Customer getCustomer() { return customer; }
  public void setCustomer(Customer customer) { this.customer = customer; }
  public Currency getCurrency() { return currency; }
  public void setCurrency(Currency currency) { this.currency = currency; }
  public BigDecimal getBalance() { return balance; }
  public void setBalance(BigDecimal balance) { this.balance = balance; }
  public OffsetDateTime getUpdatedAt() { return updatedAt; }
  public void setUpdatedAt(OffsetDateTime updatedAt) { this.updatedAt = updatedAt; }
}
