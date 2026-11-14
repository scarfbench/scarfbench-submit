package quarkus.tutorial.cart;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import quarkus.tutorial.cart.service.Cart;
import quarkus.tutorial.cart.util.BookException;

/** REST controller for cart operations. */
@RestController
@RequestMapping("/api/cart")
public class CartResource {

    private final Cart cart;

    public CartResource(Cart cart) {
        this.cart = cart;
    }

    @PostMapping("/initialize")
    public ResponseEntity<Map<String, String>> initializeCart(@RequestBody CustomerRequest request) {
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

    @PostMapping("/books/{title}")
    public ResponseEntity<Map<String, Object>> addBook(@PathVariable String title) {
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

    @DeleteMapping("/books/{title}")
    public ResponseEntity<Map<String, Object>> removeBook(@PathVariable String title) {
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

    @GetMapping("/books")
    public ResponseEntity<Map<String, Object>> getBooks() {
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

    @DeleteMapping
    public ResponseEntity<Map<String, String>> clearCart(HttpServletRequest request) {
        try {
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }
            return ResponseEntity.ok(Map.of("message", "Cart cleared successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        return ResponseEntity.ok(Map.of("status", "UP", "service", "cart-api"));
    }

    public static class CustomerRequest {
        public String customerName;
        public String customerId;
    }
}
