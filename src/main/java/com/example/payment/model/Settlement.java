package com.example.payment.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity @Table(name="settlements")
public class Settlement {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="transaction_id", nullable=false)
  private Txn transaction;
  @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="merchant_id", nullable=false)
  private Merchant merchant;
  @Column(nullable=false, precision=19, scale=2) private BigDecimal grossAmount;
  @Column(nullable=false, precision=19, scale=2) private BigDecimal fee;
  @Column(nullable=false, precision=19, scale=2) private BigDecimal netAmount;
  @Column(nullable=false) private String status; // INITIATED, SENT, COMPLETED
  @Column(nullable=false) private OffsetDateTime createdAt = OffsetDateTime.now();

  public Long getId() { return id; }
  public Txn getTransaction() { return transaction; }
  public void setTransaction(Txn transaction) { this.transaction = transaction; }
  public Merchant getMerchant() { return merchant; }
  public void setMerchant(Merchant merchant) { this.merchant = merchant; }
  public BigDecimal getGrossAmount() { return grossAmount; }
  public void setGrossAmount(BigDecimal grossAmount) { this.grossAmount = grossAmount; }
  public BigDecimal getFee() { return fee; }
  public void setFee(BigDecimal fee) { this.fee = fee; }
  public BigDecimal getNetAmount() { return netAmount; }
  public void setNetAmount(BigDecimal netAmount) { this.netAmount = netAmount; }
  public String getStatus() { return status; }
  public void setStatus(String status) { this.status = status; }
  public OffsetDateTime getCreatedAt() { return createdAt; }
}
