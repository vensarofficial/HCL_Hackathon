package com.hcl.wallet.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "customers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String customerId; // External business ID (CUST-001)

    @Column(nullable = false)
    private String fullName;

    private String email;

    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private CustomerStatus status; // ACTIVE, SUSPENDED, CLOSED
}

