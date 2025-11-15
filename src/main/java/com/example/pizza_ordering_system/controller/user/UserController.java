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
    private final FoodItemService foodItemService; // ✅ added

    public UserController(CartService cartService, FoodItemService foodItemService) {
        this.cartService = cartService;
        this.foodItemService = foodItemService;
    }

    // ✅ NEW ENDPOINT: Get all food items
    @GetMapping("/food-items")
    public ResponseEntity<ApiResponse<List<FoodItemDTO>>> getAllFoodItems() {
        List<FoodItem> foodItems = foodItemService.getAllFoodItems();

        List<FoodItemDTO> simplified = foodItems.stream()
                .map(item -> new FoodItemDTO(
                        item.getId(),
                        item.getName(),
                        item.getDescription(),
                        item.getPrice(),
                        item.getStore() != null ? item.getStore().getStoreName() : null
                ))
                .collect(Collectors.toList());

        ApiResponse<List<FoodItemDTO>> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                simplified,
                "Food items fetched successfully"
        );

        return ResponseEntity.ok(response);
    }


    @PostMapping("/cart/add")
    public ResponseEntity<ApiResponse<Cart>> addToCart(@RequestParam Long userId,
                                                       @RequestParam Long foodItemId,
                                                       @RequestParam int quantity) {
        Cart cart = cartService.addToCart(userId, foodItemId, quantity);
        return ResponseEntity.ok(new ApiResponse<>(200, cart, "Item added to cart"));
    }

    @GetMapping("/cart")
    public ResponseEntity<ApiResponse<CartDTO>> getCart(@RequestParam Long userId) {
        Cart cart = cartService.getCart(userId);

        if (cart == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(404, null, "Cart not found"));
        }

        List<CartItemDTO> itemDTOs = cart.getItems().stream()
                .map(item -> new CartItemDTO(
                        item.getId(),
                        item.getFoodItem() != null ? item.getFoodItem().getName() : null,
                        item.getQuantity(),
                        item.getTotalPrice()
                ))
                .collect(Collectors.toList());

        CartDTO cartDTO = new CartDTO(
                cart.getId(),
                cart.getUser() != null ? cart.getUser().getName() : null,
                cart.getTotalAmount(),
                itemDTOs
        );

        ApiResponse<CartDTO> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                cartDTO,
                "Cart fetched successfully"
        );

        return ResponseEntity.ok(response);
    }


    @PutMapping("/cart/update")
    public ResponseEntity<ApiResponse<Cart>> updateCartItem(@RequestParam Long userId,
                                                            @RequestParam Long cartItemId,
                                                            @RequestParam int quantity) {
        Cart cart = cartService.updateCartItem(userId, cartItemId, quantity);
        return ResponseEntity.ok(new ApiResponse<>(200, cart, "Cart updated successfully"));
    }

    @DeleteMapping("/cart/remove")
    public ResponseEntity<ApiResponse<String>> removeCartItem(@RequestParam Long userId,
                                                              @RequestParam Long cartItemId) {
        cartService.removeCartItem(userId, cartItemId);
        return ResponseEntity.ok(new ApiResponse<>(200, null, "Item removed from cart"));
    }

    @PostMapping("/order/confirm")
    public ResponseEntity<ApiResponse<String>> confirmOrder(@RequestParam Long userId) {
        return ResponseEntity.ok(new ApiResponse<>(200, cartService.confirmOrder(userId), "Order confirmed"));
    }

    @PutMapping("/order/cancel/{orderId}")
    public ResponseEntity<ApiResponse<String>> cancelOrder(@PathVariable Long orderId) {
        return ResponseEntity.ok(new ApiResponse<>(200, cartService.cancelOrder(orderId), "Order cancelled"));
    }
}
