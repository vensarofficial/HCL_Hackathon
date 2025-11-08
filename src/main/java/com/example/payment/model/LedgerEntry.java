package com.example.payment.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity @Table(name="ledger_entries")
public class LedgerEntry {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="transaction_id", nullable=false)
  private Txn transaction;

  @Column(nullable=false) private String entryType; // DEBIT_WALLET, FEE, CREDIT_MERCHANT
  @Column(nullable=false, precision=19, scale=2) private BigDecimal amount;
  @Column(nullable=false) private OffsetDateTime createdAt = OffsetDateTime.now();

  public Long getId() { return id; }
  public Txn getTransaction() { return transaction; }
  public void setTransaction(Txn transaction) { this.transaction = transaction; }
  public String getEntryType() { return entryType; }
  public void setEntryType(String entryType) { this.entryType = entryType; }
  public BigDecimal getAmount() { return amount; }
  public void setAmount(BigDecimal amount) { this.amount = amount; }
  public OffsetDateTime getCreatedAt() { return createdAt; }
}
