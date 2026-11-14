package jakarta.tutorial.decorators;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.CookieParam;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.NewCookie;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Path("/")
@Produces(MediaType.TEXT_HTML)
public class CoderResource {
    private static final String FLASH_COOKIE = "decorators-flash";
    private static final String APPLICATION_PATH = "/decorators/";
    private static final ConcurrentMap<String, String> FLASH_RESULTS = new ConcurrentHashMap<>();

    @Inject
    Coder coder;

    @GET
    public String showForm(@CookieParam(FLASH_COOKIE) String flashId) {
        String codedString = flashId == null ? null : FLASH_RESULTS.remove(flashId);
        return page("", 0, codedString, null);
    }

    @GET
    @Path("decorators")
    public String showFormAtContextPath(@CookieParam(FLASH_COOKIE) String flashId) {
        return showForm(flashId);
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response encodeOnPage(@FormParam("inputString") String input,
            @FormParam("transVal") String shiftValue) {
        return encodeWithoutRedirect(input, shiftValue);
    }

    @POST
    @Path("decorators")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response encodeAtContextPath(@FormParam("inputString") String input,
            @FormParam("transVal") String shiftValue) {
        return encodeWithoutRedirect(input, shiftValue);
    }

    @POST
    @Path("encode")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response encode(@FormParam("inputString") String input,
            @FormParam("transVal") String shiftValue, @Context UriInfo uriInfo) {
        Integer shift = parseShift(shiftValue);
        if (shift == null || shift < 0 || shift > 26) {
            return Response.ok(page(input, 0, null, "The value must be between 0 and 26.")).build();
        }
        String flashId = UUID.randomUUID().toString();
        FLASH_RESULTS.put(flashId, coder.codeString(input == null ? "" : input, shift));
        NewCookie cookie = new NewCookie.Builder(FLASH_COOKIE).value(flashId)
                .path(APPLICATION_PATH).httpOnly(true).build();
        return Response.seeOther(uriInfo.getBaseUri().resolve(APPLICATION_PATH)).cookie(cookie).build();
    }

    @POST
    @Path("reset")
    public Response reset(@Context UriInfo uriInfo) {
        NewCookie expired = new NewCookie.Builder(FLASH_COOKIE).value("")
                .path(APPLICATION_PATH).maxAge(0).build();
        return Response.seeOther(uriInfo.getBaseUri().resolve(APPLICATION_PATH)).cookie(expired).build();
    }

    private Response encodeWithoutRedirect(String input, String shiftValue) {
        Integer shift = parseShift(shiftValue);
        if (shift == null || shift < 0 || shift > 26) {
            return Response.ok(page(input, 0, null, "The value must be between 0 and 26.")).build();
        }
        String value = input == null ? "" : input;
        return Response.ok(page(value, shift, coder.codeString(value, shift), null)).build();
    }

    private static Integer parseShift(String value) {
        try { return Integer.valueOf(value); } catch (NumberFormatException exception) { return null; }
    }

    private static String page(String input, int shift, String codedString, String error) {
        String safeInput = escape(input == null ? "" : input);
        String safeResult = escape(codedString == null ? "" : codedString);
        String errorMarkup = error == null ? "" : "<li style=\"color:red\">" + escape(error) + "</li>";
        return """
                <!DOCTYPE html><html lang="en"><head><meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Decorated String Encoder</title><link rel="stylesheet" href="css/default.css"></head><body>
                <h2>Decorated String Encoder</h2><p>Enter a string and an integer, then click Encode.</p>
                <p>This will return a string that shifts the letters in the original string by the value you specify.
                The value must be between 0 and 26.</p><form action="encode" method="post">
                <label for="inputString">Enter a string: </label>
                <input type="text" id="inputString" name="inputString" value="%s">
                <label for="transVal">Enter the number of letters to shift by: </label>
                <input type="text" id="transVal" name="transVal" value="%d">
                <button type="submit">Encode</button>
                <p><strong>Result:</strong> <span style="color:blue">%s</span></p></form>
                <form action="reset" method="post"><button type="submit">Reset</button></form>
                <ul>%s</ul></body></html>
                """.formatted(safeInput, shift, safeResult, errorMarkup);
    }

    private static String escape(String value) {
        return value.replace("&", "&amp;").replace("<", "&lt;")
                .replace(">", "&gt;").replace("\"", "&quot;");
    }
}
