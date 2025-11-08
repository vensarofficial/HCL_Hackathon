package com.example.payment.model;

import jakarta.persistence.*;
import java.time.OffsetDateTime;

@Entity @Table(name="notifications")
public class Notification {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="transaction_id", nullable=false)
  private Txn transaction;
  @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="merchant_id", nullable=false)
  private Merchant merchant;
  @Column(nullable=false) private String channel;
  @Column(nullable=false, columnDefinition="text") private String payload;
  @Column(nullable=false) private OffsetDateTime createdAt = OffsetDateTime.now();

  public Long getId() { return id; }
  public Txn getTransaction() { return transaction; }
  public void setTransaction(Txn transaction) { this.transaction = transaction; }
  public Merchant getMerchant() { return merchant; }
  public void setMerchant(Merchant merchant) { this.merchant = merchant; }
  public String getChannel() { return channel; }
  public void setChannel(String channel) { this.channel = channel; }
  public String getPayload() { return payload; }
  public void setPayload(String payload) { this.payload = payload; }
  public OffsetDateTime getCreatedAt() { return createdAt; }
}
