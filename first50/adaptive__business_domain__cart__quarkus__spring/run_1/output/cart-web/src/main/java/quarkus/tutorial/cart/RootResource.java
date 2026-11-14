package quarkus.tutorial.cart;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/** Provides a lightweight service response at the application context root. */
@RestController
public class RootResource {

    @GetMapping("/")
    public ResponseEntity<Map<String, String>> root() {
        return ResponseEntity.ok(Map.of("status", "UP", "service", "cart-api"));
    }
}
