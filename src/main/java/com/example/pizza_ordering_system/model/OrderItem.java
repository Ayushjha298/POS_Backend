package com.example.pizza_ordering_system.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int quantity;
    private BigDecimal totalPrice;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "food_item_id")
    private FoodItem foodItem;

    // ---- Constructors ----
    public OrderItem() {}

    public OrderItem(Long id, int quantity, BigDecimal totalPrice, Order order, FoodItem foodItem) {
        this.id = id;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.order = order;
        this.foodItem = foodItem;
    }

    // ---- Getters and Setters ----
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public FoodItem getFoodItem() {
        return foodItem;
    }

    public void setFoodItem(FoodItem foodItem) {
        this.foodItem = foodItem;
    }

    // ---- toString ----
    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", totalPrice=" + totalPrice +
                ", orderId=" + (order != null ? order.getId() : null) +
                ", foodItem=" + (foodItem != null ? foodItem.getName() : null) +
                '}';
    }
}
