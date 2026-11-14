package jakarta.tutorial.standalone.web;

import jakarta.inject.Inject;
import jakarta.tutorial.standalone.ejb.StandaloneBean;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/")
public class GreetResource {
    @Inject
    StandaloneBean standaloneBean;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response health() {
        return Response.ok("standalone").build();
    }

    @GET
    @Path("greet")
    @Produces(MediaType.APPLICATION_JSON)
    public GreetResponse greet() {
        return new GreetResponse(standaloneBean.returnMessage());
    }

    public record GreetResponse(String message) {
    }
}
