package jakarta.tutorial.helloservice.web;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

/** Landing endpoint retained for the application's context-root health check. */
@Path("/")
public class RootResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String index() {
        return "HelloService is running.";
    }
}
