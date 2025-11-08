package com.example.payment.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity @Table(name="products")
public class Product {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name="merchant_id", nullable=false)
  private Merchant merchant;

  @Column(nullable=false, unique=true) private String sku;
  @Column(nullable=false) private String name;
  @Column(columnDefinition="text") private String description;

  @Column(nullable=false, precision=19, scale=2) private BigDecimal price;
  @Enumerated(EnumType.STRING) @Column(nullable=false) private Currency currency;

  @Column(nullable=false) private Integer stock = 0;
  @Column(nullable=false) private Boolean active = true;

  @Column(nullable=false) private OffsetDateTime createdAt = OffsetDateTime.now();
  @Column(nullable=false) private OffsetDateTime updatedAt = OffsetDateTime.now();

  public Long getId() { return id; }
  public Merchant getMerchant() { return merchant; }
  public void setMerchant(Merchant merchant) { this.merchant = merchant; }
  public String getSku() { return sku; }
  public void setSku(String sku) { this.sku = sku; }
  public String getName() { return name; }
  public void setName(String name) { this.name = name; }
  public String getDescription() { return description; }
  public void setDescription(String description) { this.description = description; }
  public BigDecimal getPrice() { return price; }
  public void setPrice(BigDecimal price) { this.price = price; }
  public Currency getCurrency() { return currency; }
  public void setCurrency(Currency currency) { this.currency = currency; }
  public Integer getStock() { return stock; }
  public void setStock(Integer stock) { this.stock = stock; }
  public Boolean getActive() { return active; }
  public void setActive(Boolean active) { this.active = active; }
  public OffsetDateTime getCreatedAt() { return createdAt; }
  public OffsetDateTime getUpdatedAt() { return updatedAt; }
  public void setUpdatedAt(OffsetDateTime updatedAt) { this.updatedAt = updatedAt; }
}
