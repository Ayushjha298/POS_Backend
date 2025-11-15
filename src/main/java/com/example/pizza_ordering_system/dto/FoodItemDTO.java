package com.example.pizza_ordering_system.dto;

import java.math.BigDecimal;

public class FoodItemDTO {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private String storeName;

    public FoodItemDTO(Long id, String name, String description, BigDecimal price, String storeName) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.storeName = storeName;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public BigDecimal getPrice() { return price; }
    public String getStoreName() { return storeName; }
}
