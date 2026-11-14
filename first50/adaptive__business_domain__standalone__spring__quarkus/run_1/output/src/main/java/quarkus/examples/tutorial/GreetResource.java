package quarkus.examples.tutorial;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/")
public class GreetResource {

    @Inject
    StandaloneBean standaloneBean;

    public record GreetResponse(String message) {
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String health() {
        return "standalone";
    }

    @GET
    @Path("greet")
    @Produces(MediaType.APPLICATION_JSON)
    public GreetResponse greet() {
        return new GreetResponse(standaloneBean.returnMessage());
    }
}
