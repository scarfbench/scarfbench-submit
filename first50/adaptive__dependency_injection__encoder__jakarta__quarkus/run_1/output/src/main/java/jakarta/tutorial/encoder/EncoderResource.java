package jakarta.tutorial.encoder;

import java.net.URI;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

/** Makes the application root behave like the original welcome-file route. */
@Path("/")
public class EncoderResource {

    @GET
    public Response index() {
        return Response.seeOther(URI.create("index.xhtml")).build();
    }
}
