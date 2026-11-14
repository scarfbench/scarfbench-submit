/*
 * Copyright (c), Eclipse Foundation, Inc. and its licensors.
 *
 * All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v1.0, which is available at
 * https://www.eclipse.org/org/documents/edl-v10.php
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */
package jakarta.tutorial.cart.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.tutorial.cart.ejb.Cart;
import jakarta.tutorial.cart.util.BookException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST resource for cart operations.
 * Provides HTTP endpoints to interact with the shopping cart service.
 */
@RestController
@RequestMapping("/api/cart")
public class CartResource {

    private final Cart cart;

    public CartResource(Cart cart) {
        this.cart = cart;
    }

    /**
     * Initialize a new cart session.
     * POST /api/cart/initialize
     * Body: {"customerName": "John Doe", "customerId": "123"}
     */
    @PostMapping("/initialize")
    public ResponseEntity<?> initializeCart(@RequestBody CustomerRequest request) {
        try {
            if (request.customerId != null && !request.customerId.isEmpty()) {
                cart.initialize(request.customerName, request.customerId);
            } else {
                cart.initialize(request.customerName);
            }
            
            Map<String, String> response = new HashMap<>();
            response.put("message", "Cart initialized successfully");
            response.put("customerName", request.customerName);
            
            return ResponseEntity.ok(response);
        } catch (BookException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Add a book to the cart.
     * POST /api/cart/books/{title}
     */
    @PostMapping("/books/{title}")
    public ResponseEntity<?> addBook(@PathVariable("title") String title) {
        try {
            cart.addBook(title);
            
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Book added successfully");
            response.put("title", title);
            response.put("cartSize", cart.getContents().size());
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Remove a book from the cart.
     * DELETE /api/cart/books/{title}
     */
    @DeleteMapping("/books/{title}")
    public ResponseEntity<?> removeBook(@PathVariable("title") String title) {
        try {
            cart.removeBook(title);
            
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Book removed successfully");
            response.put("title", title);
            response.put("cartSize", cart.getContents().size());
            
            return ResponseEntity.ok(response);
        } catch (BookException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Get all books in the cart.
     * GET /api/cart/books
     */
    @GetMapping("/books")
    public ResponseEntity<?> getBooks() {
        try {
            List<String> contents = cart.getContents();
            
            Map<String, Object> response = new HashMap<>();
            response.put("books", contents);
            response.put("count", contents.size());
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Clear the cart and end the session.
     * DELETE /api/cart
     */
    @DeleteMapping
    public ResponseEntity<?> clearCart() {
        try {
            cart.remove();
            
            return ResponseEntity.ok(Map.of("message", "Cart cleared successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Health check endpoint.
     * GET /api/cart/health
     */
    @GetMapping("/health")
    public ResponseEntity<?> health() {
        return ResponseEntity.ok(Map.of("status", "UP", "service", "cart-api"));
    }

    // Request/Response DTOs
    public static class CustomerRequest {
        public String customerName;
        public String customerId;
    }
}
