package com.chinmay.ecommerce.order_service.Service;

import com.chinmay.ecommerce.order_service.Dto.OrderRequestDto;
import com.chinmay.ecommerce.order_service.Entity.Orders;
import com.chinmay.ecommerce.order_service.Repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;

    public List<OrderRequestDto> getAllOrders() {
        log.info("Fetching all orders from the database");
        List<Orders> orders  = orderRepository.findAll();
        return orders.stream().map(order -> modelMapper.map(order,OrderRequestDto.class)).toList();
    }

    public OrderRequestDto getOrderById(Long id) {
        log.info("Fetching order with id: {}", id);
        Orders order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
        return modelMapper.map(order, OrderRequestDto.class);
    }
}
