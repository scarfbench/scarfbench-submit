package spring.examples.tutorial.cart;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.Map;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class RootResource {
    @GET
    public Map<String, String> root() {
        return Map.of("status", "UP", "service", "cart-api");
    }
}
