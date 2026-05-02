package com.chinmay.ecommerce.inventory_service.controller;

import com.chinmay.ecommerce.inventory_service.Service.ProductService;
import com.chinmay.ecommerce.inventory_service.clients.OrderFeignClient;
import com.chinmay.ecommerce.inventory_service.dto.OrderRequestDto;
import com.chinmay.ecommerce.inventory_service.dto.OrderRequestItemDto;
import com.chinmay.ecommerce.inventory_service.dto.ProductDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    // Replacement of RestClient and DiscoveryClient with Feign Client to call order service's /helloOrders endpoint
    private final OrderFeignClient orderFeignClient;

    // Calling order service from inventory service to fetch orders
    @GetMapping("/fetchOrders")
    public String fetchOrdersFromOrderService(HttpServletRequest httpServletRequest) {

        log.info(httpServletRequest.getHeader("X-Custom-Header"));
        // Fetching the order service instance from Eureka using DiscoveryClient
//        ServiceInstance orderService = discoveryClient.getInstances("order-service").getFirst();

        // Making a REST call to the order service's /helloOrders endpoint using RestClient
        // Communicating with order service using RestClient and DiscoveryClient to get the URI of the order service instance
//        return restClient.get()
//                // http://localhost:8082 is getUri() of order service instance
//                .uri(orderService.getUri() + "/orders/core/helloOrders")
//                .retrieve()
//                .body(String.class);

        // Replacing RestClient and DiscoveryClient with Feign Client to call order service's /helloOrders endpoint
        // Dont need to use Rest Client anymore
        return orderFeignClient.helloOrders();
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

    @PutMapping("/reduce-stocks")
    public ResponseEntity<Double> reduceStocks(@RequestBody OrderRequestDto orderRequestDto){
        Double totalPrice = productService.reduceStocks(orderRequestDto);
        return ResponseEntity.ok(totalPrice);
    }

}
