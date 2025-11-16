package com.example.ordering.event;

import com.example.ordering.model.OrderEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrderEventPublisher {
    private final KafkaTemplate<String, Object> kafka;
    private final String topic = "orders";

    public OrderEventPublisher(KafkaTemplate<String, Object> kafka) { this.kafka = kafka; }

    public void publishCreated(OrderEntity o) {
        kafka.send(topic, o.getTenantId() + ":" + o.getId(), new OrderEvent(o.getId(), o.getTenantId(), "CREATED"));
    }
}

record OrderEvent(java.util.UUID orderId, String tenantId, String action) {}
