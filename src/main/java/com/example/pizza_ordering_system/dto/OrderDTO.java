package com.example.pizza_ordering_system.dto;

import java.time.LocalDateTime;
import java.util.List;

public class OrderDTO {
    private Long id;
    private LocalDateTime orderDate;
    private String status;
    private String userEmail;
    private List<OrderItemDTO> items;

    public OrderDTO(Long id, LocalDateTime orderDate, String status, String userEmail, List<OrderItemDTO> items) {
        this.id = id;
        this.orderDate = orderDate;
        this.status = status;
        this.userEmail = userEmail;
        this.items = items;
    }

    public Long getId() { return id; }
    public LocalDateTime getOrderDate() { return orderDate; }
    public String getStatus() { return status; }
    public String getUserEmail() { return userEmail; }
    public List<OrderItemDTO> getItems() { return items; }
}
