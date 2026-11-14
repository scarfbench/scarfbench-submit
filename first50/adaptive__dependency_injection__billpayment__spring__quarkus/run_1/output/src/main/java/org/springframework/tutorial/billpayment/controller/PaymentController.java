package org.springframework.tutorial.billpayment.controller;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;
import org.springframework.tutorial.billpayment.aspect.Logged;
import org.springframework.tutorial.billpayment.payment.PaymentService;
import org.springframework.tutorial.billpayment.payment.PaymentType;
import jakarta.inject.Inject;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/** Web adapter preserving the original page and form routes. */
@Path("/")
@Produces(MediaType.TEXT_HTML)
public class PaymentController {
    @Inject
    PaymentService paymentService;

    @GET
    public String index() {
        return indexPage();
    }

    @POST @Path("pay") @Consumes(MediaType.APPLICATION_FORM_URLENCODED) @Logged
    public Response pay(@FormParam("paymentOption") int paymentOption,
            @FormParam("value") @NotNull @Digits(integer = 10, fraction = 2, message = "Invalid value") BigDecimal value) {
        PaymentType paymentType = paymentOption == 1 ? PaymentType.DEBIT : PaymentType.CREDIT;
        String result = paymentService.processPayment(paymentType, value);
        return Response.ok(responsePage(result, paymentType, value)).type(MediaType.TEXT_HTML_TYPE).build();
    }

    @POST @Path("reset") @Consumes(MediaType.APPLICATION_FORM_URLENCODED) @Logged
    public String reset() {
        return indexPage();
    }

    protected String indexPage() {
        return """
            <!DOCTYPE html><html lang="en"><head><meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Bill Payment Options</title><link rel="stylesheet" href="/billpayment/css/default.css"></head>
            <body><h3>Bill Payment Options</h3><p>Enter an amount, select Debit Card or Credit Card, then click Pay.</p>
            <form action="/billpayment/pay" method="post"><p><label for="value">Amount: $</label>
            <input type="number" id="value" name="value" value="0" step="0.01" min="0" required maxlength="15"></p>
            <label for="paymentOption">Options:</label><div><input type="radio" id="debit" name="paymentOption" value="1" checked><label for="debit">Debit Card</label></div>
            <div><input type="radio" id="credit" name="paymentOption" value="2"><label for="credit">Credit Card</label></div>
            <p><button type="submit">Pay</button></p></form>
            <form action="/billpayment/reset" method="post"><p><button type="submit">Reset</button></p></form></body></html>
            """;
    }

    protected String responsePage(String result, PaymentType paymentType, BigDecimal value) {
        String outcome = "success".equals(result)
                ? "<h3>Amount Paid with " + paymentType.name() + " Card: " + NumberFormat.getCurrencyInstance(Locale.US).format(value) + "</h3>"
                : "<h3 style=\"color: red;\">Payment failed. Please try again.</h3>";
        return """
            <!DOCTYPE html><html lang="en"><head><meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Bill Payment: Result</title><link rel="stylesheet" href="/billpayment/css/default.css"></head>
            <body><h2>Bill Payment: Result</h2>%s
            <form action="/billpayment/" method="get"><p><button type="submit">Back</button></p></form></body></html>
            """.formatted(outcome);
    }
}
