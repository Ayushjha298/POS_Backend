package com.example.pizza_ordering_system.dto;

import java.math.BigDecimal;

public class FoodItemDTO {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private String storeName;
    private String imageUrl;


    public FoodItemDTO(Long id, String name, String description, BigDecimal price,
                       String storeName, String imageUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.storeName = storeName;
        this.imageUrl = imageUrl;
    }


    public Long getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public BigDecimal getPrice() { return price; }
    public String getStoreName() { return storeName; }

    public String getImageUrl() { return imageUrl; }
}
