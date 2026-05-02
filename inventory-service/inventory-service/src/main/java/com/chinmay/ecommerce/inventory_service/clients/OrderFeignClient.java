package com.chinmay.ecommerce.inventory_service.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "order-service", path = "/orders")
public interface OrderFeignClient {
    @GetMapping("/core/helloOrders")
    String helloOrders();   // Method name not needs to be exact same as the method in OrderController
}
