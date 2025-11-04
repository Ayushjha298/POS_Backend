package com.example.pizza_ordering_system.repository;

import com.example.pizza_ordering_system.model.Cart;
import com.example.pizza_ordering_system.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUser(User user);
}
