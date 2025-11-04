package com.example.pizza_ordering_system.service.impl;

import com.example.pizza_ordering_system.model.FoodItem;
import com.example.pizza_ordering_system.repository.FoodItemRepository;
import com.example.pizza_ordering_system.service.interfaces.FoodItemService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodItemServiceImpl implements FoodItemService {

    private final FoodItemRepository foodItemRepository;

    public FoodItemServiceImpl(FoodItemRepository foodItemRepository) {
        this.foodItemRepository = foodItemRepository;
    }

    @Override
    public FoodItem addFoodItem(FoodItem foodItem) {
        return foodItemRepository.save(foodItem);
    }

    @Override
    public List<FoodItem> getAllFoodItems() {
        return foodItemRepository.findAll();
    }

    @Override
    public FoodItem updateFoodItem(Long id, FoodItem updatedItem) {
        FoodItem existing = foodItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Food item not found"));
        existing.setName(updatedItem.getName());
        existing.setDescription(updatedItem.getDescription());
        existing.setPrice(updatedItem.getPrice());
        existing.setStore(updatedItem.getStore());
        return foodItemRepository.save(existing);
    }

    @Override
    public void deleteFoodItem(Long id) {
        foodItemRepository.deleteById(id);
    }
}
