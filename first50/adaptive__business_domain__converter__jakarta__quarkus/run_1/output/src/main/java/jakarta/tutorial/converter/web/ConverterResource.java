/*
 * Copyright (c), Eclipse Foundation, Inc. and its licensors.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */
package jakarta.tutorial.converter.web;

import java.math.BigDecimal;

import jakarta.inject.Inject;
import jakarta.tutorial.converter.ejb.ConverterBean;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

@Path("/")
@Produces(MediaType.TEXT_HTML)
public class ConverterResource {
    @Inject
    ConverterBean converter;

    @GET
    public String get(@QueryParam("amount") String amount) {
        return render(amount);
    }

    @POST
    public String post(@QueryParam("amount") String queryAmount,
            @FormParam("amount") String formAmount) {
        return render(queryAmount != null ? queryAmount : formAmount);
    }

    private String render(String amount) {
        StringBuilder out = new StringBuilder();
        out.append("<html lang=\"en\">\n");
        out.append("<head>\n");
        out.append("<title>Servlet ConverterServlet</title>\n");
        out.append("</head>\n");
        out.append("<body>\n");
        out.append("<h1>Servlet ConverterServlet at /converter</h1>\n");
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
