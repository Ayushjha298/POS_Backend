package com.example.pizza_ordering_system.controller.user;

import com.example.pizza_ordering_system.model.Cart;
import com.example.pizza_ordering_system.model.Store;
import com.example.pizza_ordering_system.response.ApiResponse;
import com.example.pizza_ordering_system.service.interfaces.CartService;
import com.example.pizza_ordering_system.service.interfaces.StoreService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final CartService cartService;

    public UserController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/cart/add")
    public ResponseEntity<ApiResponse<Cart>> addToCart(@RequestParam Long userId,
                                                       @RequestParam Long foodItemId,
                                                       @RequestParam int quantity) {
        Cart cart = cartService.addToCart(userId, foodItemId, quantity);
        return ResponseEntity.ok(new ApiResponse<>(200, cart, "Item added to cart"));
    }

    @GetMapping("/cart")
    public ResponseEntity<ApiResponse<Cart>> getCart(@RequestParam Long userId) {
        Cart cart = cartService.getCart(userId);
        return ResponseEntity.ok(new ApiResponse<>(200, cart, "Cart fetched successfully"));
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

