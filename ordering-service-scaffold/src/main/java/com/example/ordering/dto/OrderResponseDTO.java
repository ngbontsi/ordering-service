package com.example.ordering.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.util.UUID;

@Data
public class OrderResponseDTO {
    private UUID id;
    private String customerId;
    private String status;
    private BigDecimal totalAmount;
    private String currency;
    private String items;
}
