package com.pratikesh.ecommerce.order_service.service;

import com.pratikesh.ecommerce.order_service.client.InventoryFeignClient;
import com.pratikesh.ecommerce.order_service.dto.OrderRequestDto;
import com.pratikesh.ecommerce.order_service.entity.OrderItem;
import com.pratikesh.ecommerce.order_service.entity.OrderStatus;
import com.pratikesh.ecommerce.order_service.entity.Orders;
import com.pratikesh.ecommerce.order_service.repository.OrdersRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final ModelMapper modelMapper;
    private final OrdersRepo ordersRepo;
    private final InventoryFeignClient inventoryFeignClient;

    public List<OrderRequestDto> getAllOrders(){
        log.info("Fetching all orders");
        List<Orders> orders = ordersRepo.findAll();
        return orders.stream()
                .map(order -> modelMapper.map(order, OrderRequestDto.class))
                .toList();
    }

    public OrderRequestDto getOrderById(Long id){
        log.info("Fetching order with id: {}", id);
        Orders orders = ordersRepo.findById(id)
                .orElseThrow(()-> new RuntimeException("Order not found"));
        return modelMapper.map(orders, OrderRequestDto.class);
    }

    public OrderRequestDto createOrder(OrderRequestDto orderRequestDto) {
        Double totalPrice = inventoryFeignClient.reduceStocks(orderRequestDto);

        Orders orders = modelMapper.map(orderRequestDto, Orders.class);
        for (OrderItem orderItem : orders.getItems()){
            orderItem.setOrder(orders);
        }
        orders.setTotalPrice(totalPrice);
        orders.setOrderStatus(OrderStatus.CONFIRMED);

        Orders savedOrder = ordersRepo.save(orders);
        return modelMapper.map(savedOrder, OrderRequestDto.class);
    }
}
