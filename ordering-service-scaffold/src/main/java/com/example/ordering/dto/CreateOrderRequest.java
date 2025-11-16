package com.example.ordering.dto;

import lombok.Data;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class CreateOrderRequest {
    @NotEmpty
    private String customerId;
    @NotEmpty
    private List<Item> items;
    @Data
    public static class Item {
        public String productId;
        public int quantity;
        public java.math.BigDecimal price; // optional snapshot
    }
}
