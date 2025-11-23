package com.example.pizza_ordering_system.controller.user;

import com.example.pizza_ordering_system.dto.CartDTO;
import com.example.pizza_ordering_system.dto.CartItemDTO;
import com.example.pizza_ordering_system.dto.FoodItemDTO;
import com.example.pizza_ordering_system.model.Cart;
import com.example.pizza_ordering_system.model.FoodItem;
import com.example.pizza_ordering_system.response.ApiResponse;
import com.example.pizza_ordering_system.service.interfaces.CartService;
import com.example.pizza_ordering_system.service.interfaces.FoodItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final CartService cartService;
    private final FoodItemService foodItemService;

    public UserController(CartService cartService, FoodItemService foodItemService) {
        this.cartService = cartService;
        this.foodItemService = foodItemService;
    }

    // Get all products
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
                        item.getImageUrl()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                new ApiResponse<>(200, dtoList, "Food items fetched successfully")
        );
    }

    // Add item to cart
    @PostMapping("/cart/add")
    public ResponseEntity<ApiResponse<CartDTO>> addToCart(@RequestParam Long userId,
                                                          @RequestParam Long foodItemId,
                                                          @RequestParam int quantity) {

        Cart cart = cartService.addToCart(userId, foodItemId, quantity);

        CartDTO cartDTO = new CartDTO(
                cart.getId(),
                cart.getUser().getName(),
                cart.getTotalAmount(),
                cart.getItems().stream().map(item ->
                        new CartItemDTO(
                                item.getId(),
                                item.getFoodItem().getName(),
                                item.getQuantity(),
                                item.getTotalPrice()
                        )).collect(Collectors.toList())
        );

        return ResponseEntity.ok(new ApiResponse<>(200, cartDTO, "Item added to cart"));
    }

    // Get cart
    @GetMapping("/cart")
    public ResponseEntity<ApiResponse<CartDTO>> getCart(@RequestParam Long userId) {
        Cart cart = cartService.getCart(userId);

        List<CartItemDTO> itemDTOs = cart.getItems().stream()
                .map(item -> new CartItemDTO(
                        item.getId(),
                        item.getFoodItem().getName(),
                        item.getQuantity(),
                        item.getTotalPrice()
                ))
                .collect(Collectors.toList());

        CartDTO cartDTO = new CartDTO(
                cart.getId(),
                cart.getUser().getName(),
                cart.getTotalAmount(),
                itemDTOs
        );

        return ResponseEntity.ok(new ApiResponse<>(200, cartDTO, "Cart fetched successfully"));
    }

    // Update cart
    @PutMapping("/cart/update")
    public ResponseEntity<ApiResponse<Cart>> updateCartItem(@RequestParam Long userId,
                                                            @RequestParam Long cartItemId,
                                                            @RequestParam int quantity) {
        Cart cart = cartService.updateCartItem(userId, cartItemId, quantity);
        return ResponseEntity.ok(new ApiResponse<>(200, cart, "Cart updated successfully"));
    }

    // Remove cart item
    @DeleteMapping("/cart/remove")
    public ResponseEntity<ApiResponse<String>> removeCartItem(@RequestParam Long userId,
                                                              @RequestParam Long cartItemId) {
        cartService.removeCartItem(userId, cartItemId);
        return ResponseEntity.ok(new ApiResponse<>(200, null, "Item removed from cart"));
    }

    // Place order
    @PostMapping("/order/confirm")
    public ResponseEntity<ApiResponse<String>> confirmOrder(@RequestParam Long userId) {
        String result = cartService.confirmOrder(userId);
        return ResponseEntity.ok(new ApiResponse<>(200, null, result));
    }

    // Cancel order
    @PutMapping("/order/cancel/{orderId}")
    public ResponseEntity<ApiResponse<String>> cancelOrder(@PathVariable Long orderId) {
        return ResponseEntity.ok(new ApiResponse<>(200, cartService.cancelOrder(orderId), "Order cancelled"));
    }
}
