package com.example.pizza_ordering_system.dto;

import java.math.BigDecimal;

public class CartItemDTO {
    private Long id;
    private String foodItemName;
    private int quantity;
    private BigDecimal totalPrice;

    public CartItemDTO(Long id, String foodItemName, int quantity, BigDecimal totalPrice) {
        this.id = id;
        this.foodItemName = foodItemName;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    public Long getId() { return id; }
    public String getFoodItemName() { return foodItemName; }
    public int getQuantity() { return quantity; }
    public BigDecimal getTotalPrice() { return totalPrice; }
}
