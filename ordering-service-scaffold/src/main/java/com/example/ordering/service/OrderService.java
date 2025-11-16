package com.example.ordering.service;

import com.example.ordering.model.OrderEntity;
import com.example.ordering.repository.OrderRepository;
import com.example.ordering.tenant.TenantContext;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class OrderService {
    private final OrderRepository repo;
    private final KafkaTemplate<String, Object> kafka;
    private final String topic = "orders";

    public OrderService(OrderRepository repo, KafkaTemplate<String, Object> kafka) {
        this.repo = repo;
        this.kafka = kafka;
    }

    @Transactional
    public OrderEntity createOrder(com.example.ordering.dto.CreateOrderRequest req, String idempotencyKey) {
        String tenant = TenantContext.getCurrentTenant();
        // Idempotency check should be done at controller but double-check here
        if (idempotencyKey != null) {
            var existing = repo.findByTenantIdAndIdempotencyKey(tenant, idempotencyKey);
            if (existing.isPresent()) return existing.get();
        }
        // Validate products via Product Catalog - TODO (HTTP call or cache)
        // Build snapshots from request items
        BigDecimal total = BigDecimal.ZERO;
        for (var it : req.getItems()) {
            BigDecimal price = it.getPrice() == null ? BigDecimal.ZERO : it.getPrice();
            total = total.add(price.multiply(BigDecimal.valueOf(it.getQuantity())));
        }
        OrderEntity order = OrderEntity.builder()
            .id(UUID.randomUUID())
            .tenantId(tenant)
            .customerId(req.getCustomerId())
            .status("CREATED")
            .totalAmount(total)
            .currency("ZAR")
            .items("[]")
            .idempotencyKey(idempotencyKey)
            .build();
        repo.save(order);
        // publish event
        kafka.send(topic, tenant + ":" + order.getId(), new OrderEvent(order.getId(), tenant, "CREATED"));
        return order;
    }

    public OrderEntity getById(UUID id) {
        String tenant = TenantContext.getCurrentTenant();
        return repo.findByIdAndTenantId(id, tenant).orElse(null);
    }
}

record OrderEvent(java.util.UUID orderId, String tenantId, String action) {}
