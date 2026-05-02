package com.chinmay.ecommerce.order_service.Service;

import com.chinmay.ecommerce.order_service.Client.InventoryOrdersFeignClient;
import com.chinmay.ecommerce.order_service.Dto.OrderRequestDto;
import com.chinmay.ecommerce.order_service.Entity.OrderItem;
import com.chinmay.ecommerce.order_service.Entity.Orders;
import com.chinmay.ecommerce.order_service.Enums.OrderStatus;
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
    private final InventoryOrdersFeignClient inventoryOrdersFeignClient;

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

    public OrderRequestDto createOrder(OrderRequestDto orderRequestDto) {
            // Reduce stocks in the inventory service and get the total price for the order
            // Using Feign client to call the inventory service
            Double totalPrice = inventoryOrdersFeignClient.reduceStocks(orderRequestDto);

            // Converting OrderRequestDto to Orders entity using ModelMapper
            Orders orders = modelMapper.map(orderRequestDto, Orders.class);

            for(OrderItem item : orders.getItems()) {
                // Set the order reference for each order item
                item.setOrders(orders);
            }

            // Set the total price and order status for the order
            orders.setPrice(totalPrice);
            orders.setOrderStatus(OrderStatus.CONFIRMATION);
            Orders savedOrder = orderRepository.save(orders);

            // Convert the saved order entity back to OrderRequestDto and return it
            return modelMapper.map(savedOrder, OrderRequestDto.class);
    }
}
