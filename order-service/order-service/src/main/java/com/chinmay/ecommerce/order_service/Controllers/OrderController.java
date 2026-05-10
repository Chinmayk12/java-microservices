package com.chinmay.ecommerce.order_service.Controllers;

import com.chinmay.ecommerce.order_service.Client.InventoryOrdersFeignClient;
import com.chinmay.ecommerce.order_service.Configs.FeaturesEnableConfig;
import com.chinmay.ecommerce.order_service.Dto.OrderRequestDto;
import com.chinmay.ecommerce.order_service.Service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.Order;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/core")
@RequiredArgsConstructor
@Slf4j
// This annotation is used to refresh the configuration properties of the application at runtime without restarting the application. It is typically used in conjunction with Spring Cloud Config to allow dynamic updates to configuration properties. When a configuration change is detected,
// the beans annotated with @RefreshScope will be reloaded with the new configuration values.
@RefreshScope
public class OrderController {
    private final OrderService ordersService;
    private final FeaturesEnableConfig featuresEnableConfig;

    @Value("${my.variable}")
    private String myVariable;

    @GetMapping("/helloOrders")
    // Here we are fetching the data that is passed from API Gateway to the downstream services
//    public String helloOrders(@RequestHeader("X-User-Id") String userId) {
//        log.info("Received request to /helloOrders endpoint");
//        return "Hello from Orders Service , User ID: " + userId;
//     }

    public String helloOrders() {
        log.info("Received request to /helloOrders endpoint");

        if(featuresEnableConfig.isUserTrackingEnabled()){
            return "User Tracking Enabled , myVariable: " + myVariable;
        }
        else{
            return "User Tracking Disabled , myVariable: " + myVariable;
        }
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
