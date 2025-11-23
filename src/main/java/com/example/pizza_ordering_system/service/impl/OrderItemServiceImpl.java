package com.example.pizza_ordering_system.service.impl;

import com.example.pizza_ordering_system.model.OrderItem;
import com.example.pizza_ordering_system.repository.OrderItemRepository;
import com.example.pizza_ordering_system.service.interfaces.OrderItemService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;

    public OrderItemServiceImpl(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    public List<OrderItem> getAllOrderItems() {
        return orderItemRepository.findAll();
    }
}
