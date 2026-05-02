package com.chinmay.ecommerce.inventory_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OrderRequestItemDto {
    @JsonProperty("product_id")
    private Long productId;
    private Integer quantity;
}
