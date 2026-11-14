package jakarta.tutorial.billpayment.web;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Set;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.tutorial.billpayment.payment.PaymentBean;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

/** HTTP adapter replacing the server-rendered JSF views. */
@WebServlet(urlPatterns = {"/", "/index.xhtml", "/pay", "/reset", "/response.xhtml"})
public class BillPaymentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Inject PaymentBean paymentBean;
    @Inject Validator validator;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession(true);
        if (request.getServletPath().endsWith("response.xhtml")) renderResponse(response);
        else renderForm(response, null);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getServletPath().endsWith("reset")) {
            paymentBean.reset();
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }
        String amount = request.getParameter("amount");
        if (amount == null || amount.isBlank()) {
            renderForm(response, "An amount is required.");
            return;
        }
        try {
            paymentBean.setValue(new BigDecimal(amount));
            paymentBean.setPaymentOption(Integer.parseInt(request.getParameter("paymentOption")));
        } catch (NumberFormatException exception) {
            renderForm(response, "Invalid value");
            return;
        }
        Set<ConstraintViolation<PaymentBean>> violations = validator.validate(paymentBean);
        if (!violations.isEmpty()) {
            renderForm(response, violations.iterator().next().getMessage());
            return;
        }
        paymentBean.pay();
        renderResponse(response);
    }

    private void renderForm(HttpServletResponse response, String error) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        String message = error == null ? "" : "<div class=\"messagecolor\" style=\"color:#d20005\">" + escape(error) + "</div>";
        response.getWriter().printf("""
            <!doctype html><html lang="en"><head><meta charset="UTF-8">
            <link rel="stylesheet" href="resources/css/default.css"><title>Bill Payment Options</title>
            </head><body><h3>Bill Payment Options</h3>
            <p>Enter an amount, select Debit Card or Credit Card, then click Pay.</p>
            <form id="paymentForm" method="post" action="pay">
            <label for="amt">Amount: $</label>
            <input id="amt" name="amount" type="text" required maxlength="15">
            <span>Options:</span>
            <input id="debit" type="radio" name="paymentOption" value="1" checked>
            <label for="debit">Debit Card</label>
            <input id="credit" type="radio" name="paymentOption" value="2">
            <label for="credit">Credit Card</label>
            <input id="submit" type="submit" value="Pay">
            </form>
            <form id="resetForm" method="post" action="reset"><input type="submit" value="Reset"></form>%s
            </body></html>
            """, message);
    }

    private void renderResponse(HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        String card = paymentBean.getPaymentOption() == 2 ? "Credit Card" : "Debit Card";
        BigDecimal amount = paymentBean.getValue() == null ? BigDecimal.ZERO : paymentBean.getValue();
        response.getWriter().printf("""
            <!doctype html><html lang="en"><head><meta charset="UTF-8">
            <link rel="stylesheet" href="resources/css/default.css"><title>Bill Payment: Result</title>
            </head><body><h2>Bill Payment: Result</h2><h3>Amount Paid with %s: %s</h3>
            <p><a id="back" href="index.xhtml">Back</a></p></body></html>
            """, card, NumberFormat.getCurrencyInstance(Locale.US).format(amount));
    }

    private static String escape(String value) {
        return value.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }
}
