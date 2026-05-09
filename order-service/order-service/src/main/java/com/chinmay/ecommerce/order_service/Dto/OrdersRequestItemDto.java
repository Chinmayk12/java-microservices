package com.chinmay.ecommerce.order_service.Dto;

import com.chinmay.ecommerce.order_service.Enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class OrdersRequestItemDto {
    private Long id;
    @JsonProperty("product_id")
    private Long productId;
    private Integer quantity;
}