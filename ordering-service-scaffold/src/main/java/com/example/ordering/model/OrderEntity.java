package com.example.ordering.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "orders") 
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderEntity {
    @Id
    private UUID id;
    @Column(name = "tenant_id", nullable = false)
    private String tenantId;
    private String customerId;
    private String status;
    private BigDecimal totalAmount;
    private String currency;
    @Column(columnDefinition = "jsonb")
    private String items;
    @Column(name = "idempotency_key")
    private String idempotencyKey;
}
