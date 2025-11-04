package com.example.pizza_ordering_system.controller.auth;

import com.example.pizza_ordering_system.model.User;
import com.example.pizza_ordering_system.repository.UserRepository;
import com.example.pizza_ordering_system.response.ApiError;
import com.example.pizza_ordering_system.response.ApiResponse;
import com.example.pizza_ordering_system.security.JwtTokenProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthController(UserRepository userRepository, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    // ✅ REGISTER
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            if (userRepository.findByEmail(user.getEmail()).isPresent()) {
                return new ResponseEntity<>(
                        new ApiError(HttpStatus.CONFLICT.value(), "Email already exists", "Try a different email"),
                        HttpStatus.CONFLICT
                );
            }

            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);

            ApiResponse<String> response = new ApiResponse<>(
                    HttpStatus.CREATED.value(),
                    "User registered successfully!",
                    "Registration successful"
            );
            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ApiError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Registration failed", e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    // ✅ LOGIN
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginRequest) {
        try {
            User user = userRepository.findByEmail(loginRequest.getEmail())
                    .orElseThrow(() -> new RuntimeException("Invalid credentials"));

            if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
                return new ResponseEntity<>(
                        new ApiError(HttpStatus.UNAUTHORIZED.value(), "Invalid credentials", "Incorrect email or password"),
                        HttpStatus.UNAUTHORIZED
                );
            }

            // Generate token
            String token = jwtTokenProvider.generateToken(user.getEmail(), user.getRole());

            // ✅ Include both token and role in response
            Map<String, Object> responseData = new HashMap<>();
            responseData.put("token", token);
            responseData.put("role", user.getRole());
            responseData.put("name", user.getName());

            ApiResponse<Map<String, Object>> response = new ApiResponse<>(
                    HttpStatus.OK.value(),
                    responseData,
                    "Login successful"
            );

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ApiError(HttpStatus.UNAUTHORIZED.value(), "Login failed", e.getMessage()),
                    HttpStatus.UNAUTHORIZED
            );
        }
    }

}
