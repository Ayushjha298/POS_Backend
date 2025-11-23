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

    private boolean isDeleted = false;  // 👈 Added this field

    public Store() {}

    public Store(Long id, String storeName, String location, String contactNumber, boolean isDeleted) {
        this.id = id;
        this.storeName = storeName;
        this.location = location;
        this.contactNumber = contactNumber;
        this.isDeleted = isDeleted;
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

    public boolean getIsDeleted() {     // 👈 Added getter
        return isDeleted;
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

    public void setIsDeleted(boolean isDeleted) {   // 👈 Added setter
        this.isDeleted = isDeleted;
    }
}
