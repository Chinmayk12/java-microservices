package com.chinmay.ecommerce.order_service.Dto;

import com.chinmay.ecommerce.order_service.Enums.OrderStatus;
import lombok.Data;

import java.util.List;

@Data
public class OrdersRequestItemDto {
    private Long id;
    private Long productId;
    private Integer quantity;
}
