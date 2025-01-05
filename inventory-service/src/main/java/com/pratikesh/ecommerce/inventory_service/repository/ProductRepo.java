package com.pratikesh.ecommerce.inventory_service.repository;

import com.pratikesh.ecommerce.inventory_service.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepo extends JpaRepository<ProductEntity, Long> {
}
