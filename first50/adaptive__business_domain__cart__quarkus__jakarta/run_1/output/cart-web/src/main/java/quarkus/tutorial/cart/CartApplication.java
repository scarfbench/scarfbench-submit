package quarkus.tutorial.cart;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

/** Activates Jakarta REST; resource paths retain the API routes from the Quarkus application. */
@ApplicationPath("/api")
public class CartApplication extends Application {
}
