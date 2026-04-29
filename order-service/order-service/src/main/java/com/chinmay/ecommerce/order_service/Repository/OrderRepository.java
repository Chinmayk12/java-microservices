package com.chinmay.ecommerce.order_service.Repository;

import com.chinmay.ecommerce.order_service.Entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Orders,Long> {
}
