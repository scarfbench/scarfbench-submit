/*
 * Copyright (c), Eclipse Foundation, Inc. and its licensors.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */
package jakarta.tutorial.converter.web;

import java.math.BigDecimal;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.tutorial.converter.ejb.ConverterBean;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConverterController {
    private final ConverterBean converter;

    public ConverterController(ConverterBean converter) {
        this.converter = converter;
    }

    @RequestMapping(value = "/", method = {RequestMethod.GET, RequestMethod.POST},
            produces = MediaType.TEXT_HTML_VALUE)
    public String convert(@RequestParam(required = false) String amount,
            HttpServletRequest request) {
        StringBuilder out = new StringBuilder();
        out.append("<html lang=\"en\">\n");
        out.append("<head>\n");
        out.append("<title>Servlet ConverterServlet</title>\n");
        out.append("</head>\n");
        out.append("<body>\n");
        out.append("<h1>Servlet ConverterServlet at ")
                .append(request.getContextPath()).append("</h1>\n");

        if (amount != null && !amount.isEmpty()) {
            BigDecimal dollars = new BigDecimal(amount);
            BigDecimal yenAmount = converter.dollarToYen(dollars);
            BigDecimal euroAmount = converter.yenToEuro(yenAmount);

            out.append("<p>").append(amount).append(" dollars are ")
                    .append(yenAmount.toPlainString()).append(" yen.</p>\n");
            out.append("<p>").append(yenAmount.toPlainString()).append(" yen are ")
                    .append(euroAmount.toPlainString()).append(" Euro.</p>\n");
        } else {
            out.append("<p>Enter a dollar amount to convert:</p>\n");
            out.append("<form method=\"get\">\n");
            out.append("<p>$ <input title=\"Amount\" type=\"text\" name=\"amount\" size=\"25\"></p>\n");
            out.append("<br/>\n");
            out.append("<input type=\"submit\" value=\"Submit\"><input type=\"reset\" value=\"Reset\">\n");
            out.append("</form>\n");
        }

        out.append("</body>\n");
        out.append("</html>\n");
        return out.toString();
    }
}
