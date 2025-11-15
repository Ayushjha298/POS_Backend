package com.example.pizza_ordering_system.dto;

import java.math.BigDecimal;
import java.util.List;

public class CartDTO {
    private Long id;
    private String userName;
    private BigDecimal totalAmount;
    private List<CartItemDTO> items;

    public CartDTO(Long id, String userName, BigDecimal totalAmount, List<CartItemDTO> items) {
        this.id = id;
        this.userName = userName;
        this.totalAmount = totalAmount;
        this.items = items;
    }

    public Long getId() { return id; }
    public String getUserName() { return userName; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public List<CartItemDTO> getItems() { return items; }
}
