package quarkus.examples.tutorial;

import java.math.BigDecimal;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConverterResource {

    private final ConverterBean converter;

    public ConverterResource(ConverterBean converter) {
        this.converter = converter;
    }

    @GetMapping(value = {"", "/"}, produces = MediaType.TEXT_HTML_VALUE)
    public String convert(@RequestParam(required = false) String amount,
                          HttpServletRequest request) {
        StringBuilder html = new StringBuilder();
        html.append("<html lang=\"en\">")
                .append("<head>")
                .append("<title>Servlet ConverterServlet</title>")
                .append("</head>")
                .append("<body>")
                .append("<h1>Servlet ConverterServlet at ").append(request.getContextPath()).append("/")
                .append("</h1>");

        try {
            if (amount != null && !amount.isEmpty()) {
                BigDecimal dollars = new BigDecimal(amount);
                BigDecimal yenAmount = converter.dollarToYen(dollars);
                BigDecimal euroAmount = converter.yenToEuro(yenAmount);

                html.append("<p>").append(amount).append(" dollars are ")
                        .append(yenAmount.toPlainString()).append(" yen.</p>");
                html.append("<p>").append(yenAmount.toPlainString()).append(" yen are ")
                        .append(euroAmount.toPlainString()).append(" Euro.</p>");
            } else {
                html.append("<p>Enter a dollar amount to convert:</p>")
                        .append("<form method=\"get\">")
                        .append("<p>$ <input title=\"Amount\" type=\"text\" name=\"amount\" size=\"25\"></p>")
                        .append("<br/>")
                        .append("<input type=\"submit\" value=\"Submit\">")
                        .append("<input type=\"reset\" value=\"Reset\">")
                        .append("</form>");
            }
        } finally {
            html.append("</body>")
                    .append("</html>");
        }

        return html.toString();
    }
}
