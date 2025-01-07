package com.pratikesh.ecommerce.inventory_service.service;

import com.pratikesh.ecommerce.inventory_service.dto.OrderRequestDto;
import com.pratikesh.ecommerce.inventory_service.dto.OrderRequestItemDto;
import com.pratikesh.ecommerce.inventory_service.dto.ProductDto;
import com.pratikesh.ecommerce.inventory_service.entity.ProductEntity;
import com.pratikesh.ecommerce.inventory_service.repository.ProductRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepo productRepository;
    private final ModelMapper modelMapper;

    public List<ProductDto> getAllInventory() {
        log.info("Fetching all inventory items");
        List<ProductEntity> inventories = productRepository.findAll();
        return inventories.stream()
                .map(product -> modelMapper.map(product, ProductDto.class))
                .toList();
    }

    public ProductDto getProductById(Long id) {
        log.info("Fetching Product with ID: {}", id);
        Optional<ProductEntity> inventory = productRepository.findById(id);
        return inventory.map(item -> modelMapper.map(item, ProductDto.class))
                .orElseThrow(() -> new RuntimeException("Inventory not found"));
    }

    @Transactional
    public Double reduceStock(OrderRequestDto orderRequestDto) {
        log.info("Reducing stocks");
        Double totalPrice = 0.0;
        for (OrderRequestItemDto orderRequestItemDtoDto : orderRequestDto.getItems()){
            Long productId = orderRequestItemDtoDto.getProductId();
            Integer quantity = orderRequestItemDtoDto.getQuantity();

            ProductEntity product = productRepository.findById(productId)
                    .orElseThrow(()-> new RuntimeException("Product not found with id: "+ productId));

            if(product.getStock() < quantity) throw new RuntimeException("Out of Stock");

            product.setStock(product.getStock()-quantity);
            productRepository.save(product);
            totalPrice = totalPrice + quantity * product.getPrice();
        }
        return  totalPrice;
    }
}
