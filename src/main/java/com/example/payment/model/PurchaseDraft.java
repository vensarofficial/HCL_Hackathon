package com.example.payment.model;

import jakarta.persistence.*;
import java.time.OffsetDateTime;

@Entity @Table(name="purchase_drafts")
public class PurchaseDraft {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="customer_id", nullable=false)
  private Customer customer;
  @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="product_id", nullable=false)
  private Product product;
  @Column(nullable=false) private Integer quantity;
  @Enumerated(EnumType.STRING) @Column(nullable=false) private Currency currency;
  @Column(nullable=false) private String status; // INITIATED, ... AUTHORIZED
  @Column(nullable=false) private OffsetDateTime createdAt = OffsetDateTime.now();

  public Long getId() { return id; }
  public Customer getCustomer() { return customer; }
  public void setCustomer(Customer customer) { this.customer = customer; }
  public Product getProduct() { return product; }
  public void setProduct(Product product) { this.product = product; }
  public Integer getQuantity() { return quantity; }
  public void setQuantity(Integer quantity) { this.quantity = quantity; }
  public Currency getCurrency() { return currency; }
  public void setCurrency(Currency currency) { this.currency = currency; }
  public String getStatus() { return status; }
  public void setStatus(String status) { this.status = status; }
  public OffsetDateTime getCreatedAt() { return createdAt; }
}
