package com.example.payment.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity @Table(name="transactions")
public class Txn {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="wallet_id", nullable=false)
  private Wallet wallet;

  @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="merchant_id", nullable=false)
  private Merchant merchant;

  @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="product_id", nullable=false)
  private Product product;

  @Column(nullable=false) private Integer quantity;
  @Column(nullable=false, precision=19, scale=2) private BigDecimal amount;
  @Column(nullable=false, precision=19, scale=2) private BigDecimal fee;
  @Enumerated(EnumType.STRING) @Column(nullable=false) private Currency currency;
  @Column(nullable=false) private String status;
  @Column(nullable=false) private OffsetDateTime createdAt = OffsetDateTime.now();

  public Long getId() { return id; }
  public Wallet getWallet() { return wallet; }
  public void setWallet(Wallet wallet) { this.wallet = wallet; }
  public Merchant getMerchant() { return merchant; }
  public void setMerchant(Merchant merchant) { this.merchant = merchant; }
  public Product getProduct() { return product; }
  public void setProduct(Product product) { this.product = product; }
  public Integer getQuantity() { return quantity; }
  public void setQuantity(Integer quantity) { this.quantity = quantity; }
  public BigDecimal getAmount() { return amount; }
  public void setAmount(BigDecimal amount) { this.amount = amount; }
  public BigDecimal getFee() { return fee; }
  public void setFee(BigDecimal fee) { this.fee = fee; }
  public Currency getCurrency() { return currency; }
  public void setCurrency(Currency currency) { this.currency = currency; }
  public String getStatus() { return status; }
  public void setStatus(String status) { this.status = status; }
  public OffsetDateTime getCreatedAt() { return createdAt; }
}
