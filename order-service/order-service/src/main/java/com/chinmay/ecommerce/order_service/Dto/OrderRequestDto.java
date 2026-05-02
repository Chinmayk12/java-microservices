package com.chinmay.ecommerce.order_service.Dto;

import com.chinmay.ecommerce.order_service.Enums.OrderStatus;
import lombok.Data;

import java.util.List;

@Data
public class OrderRequestDto {
    private Long id;
    private OrderStatus orderStatus;
    private Double price;
    private List<OrdersRequestItemDto> items;
}
