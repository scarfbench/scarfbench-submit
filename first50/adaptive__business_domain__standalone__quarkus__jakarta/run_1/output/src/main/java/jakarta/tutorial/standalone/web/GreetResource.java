package jakarta.tutorial.standalone.web;

import java.util.Map;

import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.tutorial.standalone.ejb.StandaloneBean;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
public class GreetResource {
    @EJB
    private StandaloneBean standaloneBean;

    @GET
    public Response health() {
        return Response.ok(Map.of("status", "UP")).build();
    }

    @GET
    @Path("greet")
    public Response greet() {
        return Response.ok(Map.of("message", standaloneBean.returnMessage())).build();
    }
}
