package com.chinmay.ecommerce.inventory_service.Service;

import com.chinmay.ecommerce.inventory_service.dto.OrderRequestDto;
import com.chinmay.ecommerce.inventory_service.dto.OrderRequestItemDto;
import org.modelmapper.ModelMapper;
import com.chinmay.ecommerce.inventory_service.dto.ProductDto;
import com.chinmay.ecommerce.inventory_service.entity.ProductEntity;
import com.chinmay.ecommerce.inventory_service.repository.ProductRepository;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public List<ProductDto> getAllInventory() {
        log.info("Fetching all products from the database");
        List<ProductEntity> inventory = productRepository.findAll();

        return inventory.stream()
                .map(product -> modelMapper.map(product, ProductDto.class))
                .toList();
    }

    public ProductDto getProductById(Long id) {
        log.info("Fetching product with id: {}", id);

        Optional<ProductEntity> productEntities = productRepository.findById(id);

        return productEntities.map(item -> modelMapper.map(item, ProductDto.class)).
                orElseThrow(()-> new RuntimeException("Product not found with id: " + id));
    }

    @Transactional
    public Double reduceStocks(OrderRequestDto orderRequestDto) {
        log.info("Reducing stocks for order: {}", orderRequestDto);
        Double totalPrice = 0.0;

        // Loop through the order items and reduce the stock for each product
        for (OrderRequestItemDto orderRequestItemDto : orderRequestDto.getItems()) {
            Long productId = orderRequestItemDto.getProductId();
            Integer quantity = orderRequestItemDto.getQuantity();

            ProductEntity product = productRepository.findById(productId)
                    .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));

            // Check if the stock is sufficient for the requested quantity
            if(product.getStock() < quantity) {
                throw new RuntimeException("Insufficient stock for product with id: " + productId);
            }

            // Reduce the stock and save the updated product entity
            product.setStock(product.getStock() - quantity);
            productRepository.save(product);

            totalPrice += quantity * product.getPrice();

        }
        return totalPrice;
    }
}
