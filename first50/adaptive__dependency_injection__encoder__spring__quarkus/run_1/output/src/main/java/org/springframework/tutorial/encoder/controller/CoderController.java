package org.springframework.tutorial.encoder.controller;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import jakarta.tutorial.encoder.Coder;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

/** Jakarta REST adapter preserving the encoder form's post/redirect/get contract. */
@Path("/")
public class CoderController {
    private static final String FLASH = "encoderFlash";
    @Inject Coder coderService;

    @GET @Produces(MediaType.TEXT_HTML)
    public Response showForm(@CookieParam(FLASH) String flash) {
        return Response.ok(page(decode(flash), null)).cookie(expired()).build();
    }

    @GET @Path("encoder") @Produces(MediaType.TEXT_HTML)
    public Response showEncoderForm(@CookieParam(FLASH) String flash) {
        return Response.ok(page(decode(flash), null, "/encoder")).cookie(expired()).build();
    }

    @POST @Path("encode") @Consumes(MediaType.APPLICATION_FORM_URLENCODED) @Produces(MediaType.TEXT_HTML)
    public Response encode(@FormParam("inputString") String input, @FormParam("transVal") String shiftText) {
        return encode(input, shiftText, "");
    }

    @POST @Path("encoder/encode") @Consumes(MediaType.APPLICATION_FORM_URLENCODED) @Produces(MediaType.TEXT_HTML)
    public Response encodeAtContextPath(@FormParam("inputString") String input,
                                        @FormParam("transVal") String shiftText) {
        return encode(input, shiftText, "/encoder");
    }

    private Response encode(String input, String shiftText, String contextPath) {
        Integer shift = parse(shiftText);
        if (shift == null || shift < 0 || shift > 26)
            return Response.ok(page("", "The value must be between 0 and 26.", contextPath)).type(MediaType.TEXT_HTML_TYPE).build();
        String output = coderService.codeString(input == null ? "" : input, shift);
        String value = Base64.getUrlEncoder().withoutPadding().encodeToString(output.getBytes(StandardCharsets.UTF_8));
        return Response.seeOther(URI.create(contextPath + "/")).cookie(new NewCookie.Builder(FLASH).value(value).path("/").httpOnly(true).build()).build();
    }

    @POST @Path("reset") @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response reset() {
        return Response.seeOther(URI.create("/")).cookie(expired()).build();
    }

    @POST @Path("encoder/reset") @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response resetAtContextPath() {
        return Response.seeOther(URI.create("/encoder/")).cookie(expired()).build();
    }

    private static Integer parse(String value) {
        try { return Integer.valueOf(value); } catch (RuntimeException ignored) { return null; }
    }
    private static String decode(String value) {
        if (value == null || value.isBlank()) return "";
        try { return new String(Base64.getUrlDecoder().decode(value), StandardCharsets.UTF_8); }
        catch (IllegalArgumentException ignored) { return ""; }
    }
    private static NewCookie expired() {
        return new NewCookie.Builder(FLASH).value("").path("/").maxAge(0).build();
    }
    private static String escape(String value) {
        if (value == null) return "";
        return value.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;")
                .replace("\"", "&quot;").replace("'", "&#39;");
    }
    private static String page(String result, String error) {
        return page(result, error, "");
    }
    private static String page(String result, String error, String contextPath) {
        return "<!DOCTYPE html><html lang=\"en\"><head><meta charset=\"UTF-8\"><meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">"
            + "<title>String Encoder</title><link rel=\"stylesheet\" href=\"/css/default.css\"></head><body><h2>String Encoder</h2>"
            + "<p>Enter a string and an integer, then click Encode.</p><p>Depending on which alternative is enabled, the coder bean will either display the argument values or return a string that shifts the letters in the original string by the value you specify. The value must be between 0 and 26.</p>"
            + "<form action=\"" + contextPath + "/encode\" method=\"post\"><p><label for=\"inputString\">Enter a string: </label><input type=\"text\" id=\"inputString\" name=\"inputString\">"
            + "<label for=\"transVal\">Enter the number of letters to shift by: </label><input type=\"text\" id=\"transVal\" name=\"transVal\"></p>"
            + "<p><button type=\"submit\">Encode</button></p><div><p><strong>Result:</strong> <span style=\"color:blue\">" + escape(result) + "</span></p></div></form>"
            + "<form action=\"" + contextPath + "/reset\" method=\"post\"><button type=\"submit\">Reset</button></form>"
            + (error == null ? "" : "<ul><li style=\"color:red\">" + escape(error) + "</li></ul>") + "</body></html>";
    }
}
