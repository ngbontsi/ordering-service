package com.example.ordering.controller;

import com.example.ordering.dto.CreateOrderRequest;
import com.example.ordering.dto.OrderResponseDTO;
import com.example.ordering.mapper.OrderMapper;
import com.example.ordering.model.OrderEntity;
import com.example.ordering.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {
    private final OrderService service;
    private final OrderMapper mapper;

    public OrderController(OrderService service, OrderMapper mapper) { this.service = service; this.mapper = mapper; }

    @Operation(summary = "Create Order")
    @PostMapping
    public ResponseEntity<OrderResponseDTO> create(@RequestHeader(value = "Idempotency-Key", required = false) String idempotencyKey,
                                                   @RequestBody @Validated CreateOrderRequest req) {
        OrderEntity created = service.createOrder(req, idempotencyKey);
        return ResponseEntity.ok(mapper.toDto(created));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> get(@PathVariable UUID id) {
        OrderEntity found = service.getById(id);
        if (found == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(mapper.toDto(found));
    }
}
