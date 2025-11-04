package com.example.pizza_ordering_system.controller.admin;

import com.example.pizza_ordering_system.model.FoodItem;
import com.example.pizza_ordering_system.model.Order;
import com.example.pizza_ordering_system.model.Store;
import com.example.pizza_ordering_system.response.ApiResponse;
import com.example.pizza_ordering_system.service.interfaces.FoodItemService;
import com.example.pizza_ordering_system.service.interfaces.OrderService;
import com.example.pizza_ordering_system.service.interfaces.StoreService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final StoreService storeService;
    private final FoodItemService foodItemService;

    private final OrderService orderService;

    public AdminController(StoreService storeService, FoodItemService foodItemService,OrderService orderService) {
        this.storeService = storeService;
        this.foodItemService = foodItemService;
        this.orderService = orderService;
    }
    @PostMapping("/stores")
    public ResponseEntity<ApiResponse<Store>> addStore(@RequestBody Store store) {
        Store savedStore = storeService.addStore(store);
        ApiResponse<Store> response = new ApiResponse<>(
                HttpStatus.CREATED.value(),
                savedStore,
                "Store added successfully"
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/stores")
    public ResponseEntity<ApiResponse<List<Store>>> getAllStores() {
        List<Store> stores = storeService.getAllStores();
        ApiResponse<List<Store>> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                stores,
                "Stores fetched successfully"
        );
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/stores/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteStore(@PathVariable Long id) {
        storeService.deleteStore(id);
        return ResponseEntity.ok(new ApiResponse<>(200, null, "Store deleted successfully"));
    }

    @PutMapping("/stores/{id}")
    public ResponseEntity<ApiResponse<Store>> updateStore(@PathVariable Long id, @RequestBody Store updatedStore) {
        Store store = storeService.updateStore(id, updatedStore);
        return ResponseEntity.ok(new ApiResponse<>(200, store, "Store updated successfully"));
    }

    @PostMapping("/food-items")
    public ResponseEntity<ApiResponse<FoodItem>> addFoodItem(@RequestBody FoodItem foodItem) {
        FoodItem savedItem = foodItemService.addFoodItem(foodItem);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(201, savedItem, "Food item added successfully"));
    }

    @GetMapping("/food-items")
    public ResponseEntity<ApiResponse<List<FoodItem>>> getAllFoodItems() {
        List<FoodItem> items = foodItemService.getAllFoodItems();
        return ResponseEntity.ok(new ApiResponse<>(200, items, "Food items fetched successfully"));
    }

    @DeleteMapping("/food-items/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteFoodItem(@PathVariable Long id) {
        foodItemService.deleteFoodItem(id);
        return ResponseEntity.ok(new ApiResponse<>(200, null, "Food item deleted successfully"));
    }

    @PutMapping("/food-items/{id}")
    public ResponseEntity<ApiResponse<FoodItem>> updateFoodItem(@PathVariable Long id, @RequestBody FoodItem updatedItem) {
        FoodItem item = foodItemService.updateFoodItem(id, updatedItem);
        return ResponseEntity.ok(new ApiResponse<>(200, item, "Food item updated successfully"));
    }

    @PutMapping("/orders/{id}/status")
    public ResponseEntity<ApiResponse<Order>> changeOrderStatus(
            @PathVariable Long id,
            @RequestParam String status) {

        Order updatedOrder = orderService.updateOrderStatus(id, status);
        return ResponseEntity.ok(new ApiResponse<>(200, updatedOrder, "Order status updated successfully"));
    }


}
