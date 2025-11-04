package com.example.pizza_ordering_system.service.interfaces;

import com.example.pizza_ordering_system.model.Cart;

public interface CartService {
    Cart addToCart(Long userId, Long foodItemId, int quantity);
    Cart getCart(Long userId);
    Cart updateCartItem(Long userId, Long cartItemId, int quantity);
    void removeCartItem(Long userId, Long cartItemId);
    String confirmOrder(Long userId);
    String cancelOrder(Long orderId);
}
