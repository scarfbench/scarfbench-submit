package jakarta.tutorial.decorators;

import jakarta.inject.Inject;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "coderServlet", urlPatterns = {"", "/index.xhtml", "/encode", "/reset"}, loadOnStartup = 1)
public class CoderServlet extends HttpServlet {
    @Inject
    private Coder coder;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        render(request, response, null, null, null);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if ("/reset".equals(request.getServletPath())) {
            request.getSession().removeAttribute("codedString");
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }
        String source = request.getParameter("inputString");
        String rawShift = request.getParameter("transVal");
        int shift;
        try {
            shift = Integer.parseInt(rawShift);
        } catch (NumberFormatException exception) {
            render(request, response, source, rawShift, "The value must be between 0 and 26.");
            return;
        }
        if (shift < 0 || shift > 26) {
            render(request, response, source, rawShift, "The value must be between 0 and 26.");
            return;
        }
        request.getSession().setAttribute("codedString", coder.codeString(source == null ? "" : source, shift));
        response.sendRedirect(request.getContextPath() + "/");
    }

    private void render(HttpServletRequest request, HttpServletResponse response,
            String source, String shift, String error) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        Object coded = request.getSession().getAttribute("codedString");
        try (PrintWriter writer = response.getWriter()) {
            writer.append("<!DOCTYPE html><html lang=\"en\"><head><meta charset=\"UTF-8\"><title>Decorated String Encoder</title>")
                    .append("<link rel=\"stylesheet\" href=\"").append(request.getContextPath()).append("/css/default.css\"></head><body>")
                    .append("<h2>Decorated String Encoder</h2><p>Enter a string and an integer, then click Encode.</p>")
                    .append("<p>This will return a string that shifts the letters in the original string by the value you specify. The value must be between 0 and 26.</p>")
                    .append("<form action=\"").append(request.getContextPath()).append("/encode\" method=\"post\">")
                    .append("<label for=\"inputString\">Enter a string: </label><input id=\"inputString\" name=\"inputString\" value=\"")
                    .append(escape(source)).append("\"><label for=\"transVal\">Enter the number of letters to shift by: </label>")
                    .append("<input id=\"transVal\" name=\"transVal\" value=\"").append(escape(shift)).append("\">")
                    .append("<button type=\"submit\">Encode</button><p><strong>Result:</strong> <span style=\"color:blue\">")
                    .append(escape(coded == null ? null : coded.toString())).append("</span></p></form>")
                    .append("<form action=\"").append(request.getContextPath()).append("/reset\" method=\"post\"><button type=\"submit\">Reset</button></form>");
            if (error != null) writer.append("<p style=\"color:red\">").append(escape(error)).append("</p>");
            writer.append("</body></html>");
        }
    }

    private static String escape(String value) {
        if (value == null) return "";
        return value.replace("&", "&amp;").replace("<", "&lt;")
                .replace(">", "&gt;").replace("\"", "&quot;");
    }
}
