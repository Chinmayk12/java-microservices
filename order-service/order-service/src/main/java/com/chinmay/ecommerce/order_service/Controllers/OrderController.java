package com.chinmay.ecommerce.order_service.Controllers;

import com.chinmay.ecommerce.order_service.Client.InventoryOrdersFeignClient;
import com.chinmay.ecommerce.order_service.Dto.OrderRequestDto;
import com.chinmay.ecommerce.order_service.Service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/core")
@RequiredArgsConstructor
@Slf4j
public class OrderController {
    private final OrderService ordersService;

    @GetMapping("/helloOrders")
    // Here we are fetching the data that is passed from API Gateway to the downstream services
    public String helloOrders(@RequestHeader("X-User-Id") String userId) {
        log.info("Received request to /helloOrders endpoint");
        return "Hello from Orders Service , User ID: " + userId;
    }

    @PostMapping("/create-order")
    public ResponseEntity<OrderRequestDto> createOrder(@RequestBody OrderRequestDto orderRequestDto){
       OrderRequestDto orderRequestDto1 = ordersService.createOrder(orderRequestDto);
       return ResponseEntity.ok(orderRequestDto1);
    }

    @GetMapping
    public ResponseEntity<List<OrderRequestDto>> getAllOrders(HttpServletRequest request) {
        log.info("Fetching all orders from controller");
        List<OrderRequestDto> orders = ordersService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderRequestDto> getOrderById(@PathVariable Long id) {
        log.info("Fetching order with id: {} from controller", id);
        OrderRequestDto order = ordersService.getOrderById(id);
        return ResponseEntity.ok(order);
    }
}
