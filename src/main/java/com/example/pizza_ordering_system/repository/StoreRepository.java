package com.example.pizza_ordering_system.repository;

import com.example.pizza_ordering_system.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long> {
}
