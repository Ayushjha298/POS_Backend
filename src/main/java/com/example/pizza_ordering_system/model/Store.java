package com.example.pizza_ordering_system.model;

import jakarta.persistence.*;

@Entity
@Table(name = "stores")
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String storeName;
    private String location;
    private String contactNumber;

    public Store() {}

    public Store(Long id, String storeName, String location, String contactNumber) {
        this.id = id;
        this.storeName = storeName;
        this.location = location;
        this.contactNumber = contactNumber;
    }

    public Long getId() {
        return id;
    }

    public String getStoreName() {
        return storeName;
    }

    public String getLocation() {
        return location;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }
}
