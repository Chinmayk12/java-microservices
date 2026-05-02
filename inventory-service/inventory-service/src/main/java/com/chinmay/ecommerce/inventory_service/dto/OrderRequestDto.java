package com.chinmay.ecommerce.inventory_service.dto;

import lombok.Data;
import java.util.List;

@Data
public class OrderRequestDto {
    List<OrderRequestItemDto> items;
}
