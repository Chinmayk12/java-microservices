package com.chinmay.ecommerce.order_service.Dto;

import lombok.Data;

@Data
public class OrderRequestDto {
    private Long id;
    private com.chinmay.ecommerce.order_service.Enums.OrderStatus orderStatus;
    private Double price;
    private java.util.List<OrdersRequestItemDto> items;
}
