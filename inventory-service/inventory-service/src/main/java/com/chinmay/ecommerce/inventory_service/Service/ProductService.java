package com.chinmay.ecommerce.inventory_service.Service;

import org.modelmapper.ModelMapper;
import com.chinmay.ecommerce.inventory_service.dto.ProductDto;
import com.chinmay.ecommerce.inventory_service.entity.ProductEntity;
import com.chinmay.ecommerce.inventory_service.repository.ProductRepository;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

}
