package com.example.pizza_ordering_system.dto;

import java.math.BigDecimal;

public class OrderItemDTO {
    private Long id;
    private int quantity;
    private BigDecimal totalPrice;
    private String foodItemName;

    public OrderItemDTO(Long id, int quantity, BigDecimal totalPrice, String foodItemName) {
        this.id = id;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.foodItemName = foodItemName;
    }

    public Long getId() { return id; }
    public int getQuantity() { return quantity; }
    public BigDecimal getTotalPrice() { return totalPrice; }
    public String getFoodItemName() { return foodItemName; }
}
