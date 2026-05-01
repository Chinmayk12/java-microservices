package com.chinmay.ecommerce.inventory_service.controller;

import com.chinmay.ecommerce.inventory_service.Service.ProductService;
import com.chinmay.ecommerce.inventory_service.dto.ProductDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;


import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    // This talks to Eureka.
    private final DiscoveryClient discoveryClient;
    private final RestClient restClient;

    // Calling order service from inventory service to fetch orders
    @GetMapping("/fetchOrders")
    public String fetchOrdersFromOrderService() {

        // Fetching the order service instance from Eureka using DiscoveryClient
        ServiceInstance orderService =
                discoveryClient.getInstances("order-service").getFirst();

        // Making a REST call to the order service's /helloOrders endpoint using RestClient
        return restClient.get()
                // http://localhost:8082 is getUri() of order service instance
                .uri(orderService.getUri() + "/api/v1/orders/helloOrders")
                .retrieve()
                .body(String.class);
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllInventory() {
        log.info("Received request to fetch all products");
        List<ProductDto> inventory = productService.getAllInventory();
        return ResponseEntity.ok(inventory);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
        log.info("Received request to fetch product with id: {}", id);
        ProductDto product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

}
