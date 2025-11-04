package com.example.pizza_ordering_system.model;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;

@Entity
@Table(name = "cart_items")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    @com.fasterxml.jackson.annotation.JsonIgnore
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "food_item_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private FoodItem foodItem;

    private int quantity;
    private BigDecimal totalPrice;

    public CartItem() {}

    public CartItem(Long id, Cart cart, FoodItem foodItem, int quantity, BigDecimal totalPrice) {
        this.id = id;
        this.cart = cart;
        this.foodItem = foodItem;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Cart getCart() { return cart; }
    public void setCart(Cart cart) { this.cart = cart; }

    public FoodItem getFoodItem() { return foodItem; }
    public void setFoodItem(FoodItem foodItem) { this.foodItem = foodItem; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public BigDecimal getTotalPrice() { return totalPrice; }
    public void setTotalPrice(BigDecimal totalPrice) { this.totalPrice = totalPrice; }
}
