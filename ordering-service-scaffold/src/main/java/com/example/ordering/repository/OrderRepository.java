package com.example.ordering.repository;

import com.example.ordering.model.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<OrderEntity, UUID> {
    Optional<OrderEntity> findByIdAndTenantId(UUID id, String tenantId);
    Optional<OrderEntity> findByTenantIdAndIdempotencyKey(String tenantId, String idempotencyKey);
}
