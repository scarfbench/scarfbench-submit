package jakarta.tutorial.decorators;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

/** HTTP adapter replacing the managed-server JSF view. */
@Path("/")
@Produces(MediaType.TEXT_HTML)
public class CoderResource {

    @Inject
    CoderBean coderBean;

    @GET
    public String index() {
        return render("", 0, null, null);
    }

    @GET
    @Path("index.xhtml")
    public String legacyIndex() {
        return index();
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public String encode(@FormParam("inputString") String inputString,
                         @FormParam("transVal") String transVal,
                         @FormParam("action") String action) {
        return submit(inputString, transVal, action);
    }

    @POST
    @Path("index.xhtml")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public String legacyEncode(@FormParam("inputString") String inputString,
                               @FormParam("transVal") String transVal,
                               @FormParam("action") String action) {
        return submit(inputString, transVal, action);
    }

    private String submit(String inputString, String transVal, String action) {
        if ("reset".equalsIgnoreCase(action)) {
            coderBean.reset();
            return render("", 0, null, null);
        }
        String input = inputString == null ? "" : inputString;
        final int shift;
        try {
            shift = Integer.parseInt(transVal == null ? "" : transVal.trim());
        } catch (NumberFormatException exception) {
            return render(input, 0, null, "The value must be an integer between 0 and 26.");
        }
        if (shift < 0 || shift > 26) {
            return render(input, shift, null, "The value must be between 0 and 26.");
        }
        coderBean.setInputString(input);
        coderBean.setTransVal(shift);
        coderBean.encodeString();
        return render(input, shift, coderBean.getCodedString(), null);
    }

    private static String render(String input, int shift, String result, String error) {
        String message = error == null ? "" : "<div class=\"messagecolor\" role=\"alert\">" + escape(error) + "</div>";
        String output = result == null ? "" : escape(result);
        return """
                <!doctype html>
                <html lang="en">
                <head>
                  <meta charset="UTF-8">
                  <title>Decorated String Encoder</title>
                  <link rel="stylesheet" href="/decorators/resources/css/default.css">
                </head>
                <body>
                  <h2>Decorated String Encoder</h2>
                  <p>Enter a string and an integer, then click Encode.</p>
                  <p>The coder bean will return a string that shifts the letters in the original string by the value you specify.
                     The value must be between 0 and 26.</p>
                  <form id="encodeit" method="post">
                    <p><label for="inputString">Enter a string: </label>
                       <input id="inputString" name="inputString" value="%s" />
                       <label for="transVal">Enter the number of letters to shift by: </label>
                       <input id="transVal" name="transVal" type="number" min="0" max="26" value="%d" /></p>
                    <p><input type="submit" name="action" value="Encode" /></p>
                    <p><label for="outputString">Result: </label>
                       <span id="outputString" value="%s" style="color:blue">%s</span></p>
                    <p><input type="submit" name="action" value="Reset" /></p>
                  </form>
                  %s
                </body>
                </html>
                """.formatted(escape(input), shift, output, output, message);
    }

    private static String escape(String value) {
        return value.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;")
                .replace("\"", "&quot;").replace("'", "&#39;");
    }
}
