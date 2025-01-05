package com.pratikesh.ecommerce.order_service.repository;

import com.pratikesh.ecommerce.order_service.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersRepo extends JpaRepository<Orders, Long> {
}
