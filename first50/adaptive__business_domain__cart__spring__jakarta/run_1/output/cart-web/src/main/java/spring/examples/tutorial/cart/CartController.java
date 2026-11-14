package spring.examples.tutorial.cart;

import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import spring.examples.tutorial.cart.service.Cart;
import spring.examples.tutorial.cart.util.BookException;

@Path("")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CartController {
    @Inject
    private Cart cart;

    @POST
    @Path("/initialize")
    public Response initializeCart(CustomerRequest request) {
        try {
            if (request.customerId != null && !request.customerId.isEmpty()) {
                cart.initialize(request.customerName, request.customerId);
            } else {
                cart.initialize(request.customerName);
            }
            Map<String, String> response = new HashMap<>();
            response.put("message", "Cart initialized successfully");
            response.put("customerName", request.customerName);
            return Response.ok(response).build();
        } catch (BookException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(Map.of("error", e.getMessage())).build();
        }
    }

    @POST
    @Path("/books/{title}")
    public Response addBook(@PathParam("title") String title) {
        try {
            cart.addBook(title);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Book added successfully");
            response.put("title", title);
            response.put("cartSize", cart.getContents().size());
            return Response.ok(response).build();
        } catch (Exception e) {
            return Response.serverError().entity(Map.of("error", e.getMessage())).build();
        }
    }

    @DELETE
    @Path("/books/{title}")
    public Response removeBook(@PathParam("title") String title) {
        try {
            cart.removeBook(title);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Book removed successfully");
            response.put("title", title);
            response.put("cartSize", cart.getContents().size());
            return Response.ok(response).build();
        } catch (BookException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(Map.of("error", e.getMessage())).build();
        }
    }

    @GET
    @Path("/books")
    public Response getBooks() {
        try {
            List<String> contents = cart.getContents();
            Map<String, Object> response = new HashMap<>();
            response.put("books", contents);
            response.put("count", contents.size());
            return Response.ok(response).build();
        } catch (Exception e) {
            return Response.serverError().entity(Map.of("error", e.getMessage())).build();
        }
    }

    @DELETE
    public Response clearCart(@Context HttpServletRequest request) {
        try {
            cart.remove();
            request.getSession().invalidate();
            return Response.ok(Map.of("message", "Cart cleared successfully")).build();
        } catch (Exception e) {
            return Response.serverError().entity(Map.of("error", e.getMessage())).build();
        }
    }

    @GET
    @Path("/health")
    public Response health() {
        return Response.ok(Map.of("status", "UP", "service", "cart-api")).build();
    }

    public static class CustomerRequest {
        public String customerName;
        public String customerId;
    }
}
