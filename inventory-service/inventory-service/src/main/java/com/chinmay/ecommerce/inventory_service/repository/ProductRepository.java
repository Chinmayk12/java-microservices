package com.chinmay.ecommerce.inventory_service.repository;

import com.chinmay.ecommerce.inventory_service.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
}
