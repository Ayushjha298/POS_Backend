package com.example.pizza_ordering_system.service.impl;

import com.example.pizza_ordering_system.model.*;
import com.example.pizza_ordering_system.repository.*;
import com.example.pizza_ordering_system.service.interfaces.CartService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final FoodItemRepository foodItemRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    public CartServiceImpl(CartRepository cartRepository,
                           CartItemRepository cartItemRepository,
                           FoodItemRepository foodItemRepository,
                           UserRepository userRepository,
                           OrderRepository orderRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.foodItemRepository = foodItemRepository;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public Cart addToCart(Long userId, Long foodItemId, int quantity) {
        User user = userRepository.findById(userId).orElseThrow();
        FoodItem food = foodItemRepository.findById(foodItemId).orElseThrow();

        Cart cart = cartRepository.findByUser(user).orElseGet(() -> {
            Cart newCart = new Cart();
            newCart.setUser(user);
            newCart.setItems(new ArrayList<>());
            return cartRepository.save(newCart);
        });

        CartItem item = new CartItem();
        item.setCart(cart);
        item.setFoodItem(food);
        item.setQuantity(quantity);
        item.setTotalPrice(food.getPrice().multiply(BigDecimal.valueOf(quantity)));
        cartItemRepository.save(item);

        cart.getItems().add(item);
        BigDecimal total = cart.getItems().stream()
                .map(CartItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        cart.setTotalAmount(total);

        return cartRepository.save(cart);
    }

    @Override
    public Cart getCart(Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        return cartRepository.findByUser(user).orElseThrow();
    }

    @Override
    public Cart updateCartItem(Long userId, Long cartItemId, int quantity) {
        CartItem item = cartItemRepository.findById(cartItemId).orElseThrow();
        item.setQuantity(quantity);
        item.setTotalPrice(item.getFoodItem().getPrice().multiply(BigDecimal.valueOf(quantity)));
        cartItemRepository.save(item);
        return getCart(userId);
    }

    @Override
    public void removeCartItem(Long userId, Long cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }
    @Override
    public String confirmOrder(Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        Cart cart = cartRepository.findByUser(user).orElseThrow();

        Order order = new Order();
        order.setUser(user);
        order.setStatus("PLACED");
        orderRepository.save(order);

        cartRepository.delete(cart);
        return "Order placed successfully!";
    }

    @Override
    public String cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow();
        order.setStatus("CANCELLED");
        orderRepository.save(order);
        return "Order cancelled successfully!";
    }
}

