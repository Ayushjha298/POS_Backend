package com.example.pizza_ordering_system.service.interfaces;

import com.example.pizza_ordering_system.model.Order;

import java.util.List;

public interface OrderService {
    List<Order> getAllOrders();
    Order updateOrderStatus(Long id, String status);
}
