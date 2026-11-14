package quarkus.examples.tutorial;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;

import jakarta.inject.Inject;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/")
public class ConverterResource extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Inject
    private ConverterBean converter;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        render(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        render(request, response);
    }

    private void render(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        String amount = request.getParameter("amount");

        try (PrintWriter out = response.getWriter()) {
            out.append("<html lang=\"en\"><head><title>Servlet ConverterServlet</title></head><body>")
                    .append("<h1>Servlet ConverterServlet at ")
                    .append(request.getContextPath()).append("</h1>");

            if (amount != null && !amount.isEmpty()) {
                BigDecimal dollars = new BigDecimal(amount);
                BigDecimal yenAmount = converter.dollarToYen(dollars);
                BigDecimal euroAmount = converter.yenToEuro(yenAmount);
                out.append("<p>").append(amount).append(" dollars are ")
                        .append(yenAmount.toPlainString()).append(" yen.</p>")
                        .append("<p>").append(yenAmount.toPlainString()).append(" yen are ")
                        .append(euroAmount.toPlainString()).append(" Euro.</p>");
            } else {
                out.append("<p>Enter a dollar amount to convert:</p>")
                        .append("<form method=\"get\">")
                        .append("<p>$ <input title=\"Amount\" type=\"text\" name=\"amount\" size=\"25\"></p>")
                        .append("<br/><input type=\"submit\" value=\"Submit\">")
                        .append("<input type=\"reset\" value=\"Reset\"></form>");
            }
            out.append("</body></html>");
        }
    }
}
