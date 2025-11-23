package com.example.pizza_ordering_system.controller.admin;

import com.example.pizza_ordering_system.dto.FoodItemDTO;
import com.example.pizza_ordering_system.dto.OrderDTO;
import com.example.pizza_ordering_system.dto.OrderItemDTO;
import com.example.pizza_ordering_system.model.FoodItem;
import com.example.pizza_ordering_system.model.Order;
import com.example.pizza_ordering_system.model.OrderItem;
import com.example.pizza_ordering_system.model.Store;
import com.example.pizza_ordering_system.response.ApiResponse;
import com.example.pizza_ordering_system.service.interfaces.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final StoreService storeService;
    private final FoodItemService foodItemService;
    private final ImageUploadService imageUploadService;

    private final OrderItemService orderItemService;


    private final OrderService orderService;

    public AdminController(StoreService storeService, FoodItemService foodItemService,OrderService orderService,ImageUploadService imageUploadService,OrderItemService orderItemService  ) {
        this.storeService = storeService;
        this.foodItemService = foodItemService;
        this.orderService = orderService;
        this.imageUploadService = imageUploadService;
        this.orderItemService = orderItemService;
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
        storeService.deleteStore(id);  // now marks isDeleted = true
        return ResponseEntity.ok(new ApiResponse<>(200, null, "Store deleted successfully"));
    }


    @PutMapping("/stores/{id}")
    public ResponseEntity<ApiResponse<Store>> updateStore(@PathVariable Long id, @RequestBody Store updatedStore) {
        Store store = storeService.updateStore(id, updatedStore);
        return ResponseEntity.ok(new ApiResponse<>(200, store, "Store updated successfully"));
    }

//    @PostMapping(value = "/food-items", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<ApiResponse<FoodItem>> addFoodItem(
//            @RequestPart("foodItem") String foodItemJson,
//            @RequestPart("image") MultipartFile image
//    ) throws IOException {
//
//        // Convert JSON string to FoodItem
//        ObjectMapper mapper = new ObjectMapper();
//        FoodItem foodItem = mapper.readValue(foodItemJson, FoodItem.class);
//
//        // Upload image
//        String imageUrl = imageUploadService.uploadImage(image);
//        foodItem.setImageUrl(imageUrl);
//
//        FoodItem savedItem = foodItemService.addFoodItem(foodItem);
//
//        return ResponseEntity.status(HttpStatus.CREATED)
//                .body(new ApiResponse<>(201, savedItem, "Food item added successfully"));
//    }


    @PostMapping(value = "/food-items", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<FoodItem>> addFoodItem(
            @RequestPart("foodItem") String foodItemJson,
            @RequestPart("image") MultipartFile image
    ) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        FoodItem foodItem = mapper.readValue(foodItemJson, FoodItem.class);

        // 🔥 FIX — FETCH STORE FROM DB BEFORE SAVING
        if (foodItem.getStore() != null && foodItem.getStore().getId() != null) {
            Long storeId = foodItem.getStore().getId();
            Store store = storeService.getStoreById(storeId);  // Load full entity
            foodItem.setStore(store); // Attach store entity
        }

        // Upload image
        String imageUrl = imageUploadService.uploadImage(image);
        foodItem.setImageUrl(imageUrl);

        FoodItem savedItem = foodItemService.addFoodItem(foodItem);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(201, savedItem, "Food item added successfully"));
    }


    @GetMapping("/food-items")
    public ResponseEntity<ApiResponse<List<FoodItemDTO>>> getAllFoodItems() {
        List<FoodItem> items = foodItemService.getAllFoodItems();

        List<FoodItemDTO> dtoList = items.stream()
                .map(item -> new FoodItemDTO(
                        item.getId(),
                        item.getName(),
                        item.getDescription(),
                        item.getPrice(),
                        item.getStore() != null ? item.getStore().getStoreName() : null,
                        item.getImageUrl() // 👈 ADDED IMAGE URL
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                new ApiResponse<>(200, dtoList, "Food items fetched successfully")
        );
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

    @GetMapping("/orders")
    public ResponseEntity<ApiResponse<List<OrderDTO>>> getAllOrders() {

        List<Order> orders = orderService.getAllOrders();

        List<OrderDTO> dtoList = orders.stream()
                .map(order -> new OrderDTO(
                        order.getId(),
                        order.getOrderDate(),
                        order.getStatus(),
                        order.getUser() != null ? order.getUser().getEmail() : null,
                        order.getOrderItems().stream()
                                .map(item -> new OrderItemDTO(
                                        item.getId(),
                                        item.getQuantity(),
                                        item.getTotalPrice(),
                                        item.getFoodItem() != null ? item.getFoodItem().getName() : null
                                ))
                                .collect(Collectors.toList())   // ✅ FIXED
                ))
                .collect(Collectors.toList());   // ✅ FIXED

        return ResponseEntity.ok(new ApiResponse<>(200, dtoList, "Orders fetched successfully"));
    }


    @GetMapping("/order-items")
    public ResponseEntity<ApiResponse<List<OrderItemDTO>>> getAllOrderItems() {

        List<OrderItem> items = orderItemService.getAllOrderItems();

        List<OrderItemDTO> dtoList = items.stream()
                .map(item -> new OrderItemDTO(
                        item.getId(),
                        item.getQuantity(),
                        item.getTotalPrice(),
                        item.getFoodItem() != null ? item.getFoodItem().getName() : null
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                new ApiResponse<>(200, dtoList, "Order items fetched successfully")
        );
    }




    @PutMapping("/orders/{id}/status")
    public ResponseEntity<ApiResponse<Order>> changeOrderStatus(
            @PathVariable Long id,
            @RequestParam String status) {

        Order updatedOrder = orderService.updateOrderStatus(id, status);
        return ResponseEntity.ok(new ApiResponse<>(200, updatedOrder, "Order status updated successfully"));
    }


}
