package com.example.payment.model;

import jakarta.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "idempotency_keys")
public class IdempotencyKey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 128)
    private String idempotencyKey;

    @Column(nullable = false)
    private String status; // IN_PROGRESS, COMPLETED

    @Column(nullable = false)
    private OffsetDateTime createdAt = OffsetDateTime.now();

    // ✅ Store JSON as plain text
    // ✅ Allows null during first request
    @Column(nullable = true)
    private String responseSnapshot;

    public Long getId() { return id; }

    public String getIdempotencyKey() { return idempotencyKey; }
    public void setIdempotencyKey(String idempotencyKey) { this.idempotencyKey = idempotencyKey; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public OffsetDateTime getCreatedAt() { return createdAt; }

    public String getResponseSnapshot() { return responseSnapshot; }
    public void setResponseSnapshot(String responseSnapshot) { this.responseSnapshot = responseSnapshot; }
}
