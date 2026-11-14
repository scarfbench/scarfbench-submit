/* Copyright (c), Eclipse Foundation, Inc. and its licensors. SPDX-License-Identifier: BSD-3-Clause */
package org.springframework.tutorial.billpayment.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.NumberFormat;

import org.springframework.tutorial.billpayment.aspect.Logged;
import org.springframework.tutorial.billpayment.payment.PaymentService;
import org.springframework.tutorial.billpayment.payment.PaymentType;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/** HTTP adapter preserving the original root, pay, reset, and CSS routes. */
@WebServlet(urlPatterns = "/")
public class PaymentController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Inject
    private PaymentService paymentService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if ("/css/default.css".equals(request.getServletPath())) {
            request.getRequestDispatcher("/WEB-INF/css/default.css").forward(request, response);
            return;
        }
        renderIndex(request, response, null);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if ("/reset".equals(request.getServletPath())) {
            renderIndex(request, response, null);
        } else if ("/pay".equals(request.getServletPath())) {
            pay(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Logged
    private void pay(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            int option = Integer.parseInt(request.getParameter("paymentOption"));
            BigDecimal value = new BigDecimal(request.getParameter("value"));
            if (value.precision() - value.scale() > 10 || Math.max(value.scale(), 0) > 2) {
                throw new IllegalArgumentException("Invalid value");
            }
            PaymentType paymentType = option == 1 ? PaymentType.DEBIT : PaymentType.CREDIT;
            renderResponse(request, response, paymentService.processPayment(paymentType, value), paymentType, value);
        } catch (IllegalArgumentException | NullPointerException exception) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            renderIndex(request, response, "Invalid value");
        }
    }

    private void renderIndex(HttpServletRequest request, HttpServletResponse response, String error)
            throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        String context = request.getContextPath();
        PrintWriter out = response.getWriter();
        out.print("<!DOCTYPE html><html lang=\"en\"><head><meta charset=\"UTF-8\">"
                + "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">"
                + "<title>Bill Payment Options</title><link rel=\"stylesheet\" href=\"" + context
                + "/css/default.css\"></head><body><h3>Bill Payment Options</h3>"
                + "<p>Enter an amount, select Debit Card or Credit Card, then click Pay.</p>"
                + "<form action=\"" + context + "/pay\" method=\"post\"><p>"
                + "<label for=\"value\">Amount: $</label><input type=\"number\" id=\"value\" name=\"value\" "
                + "value=\"0\" step=\"0.01\" min=\"0\" required maxlength=\"15\"></p>"
                + "<label>Options:</label><div><input type=\"radio\" id=\"debit\" name=\"paymentOption\" "
                + "value=\"1\" checked><label for=\"debit\">Debit Card</label></div>"
                + "<div><input type=\"radio\" id=\"credit\" name=\"paymentOption\" value=\"2\">"
                + "<label for=\"credit\">Credit Card</label></div><p><button type=\"submit\">Pay</button></p>"
                + "</form><form action=\"" + context + "/reset\" method=\"post\">"
                + "<p><button type=\"submit\">Reset</button></p></form>");
        if (error != null) {
            out.print("<div class=\"messagecolor\"><p>Invalid value</p></div>");
        }
        out.print("</body></html>");
    }

    private void renderResponse(HttpServletRequest request, HttpServletResponse response, String result,
            PaymentType paymentType, BigDecimal value) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        String context = request.getContextPath();
        PrintWriter out = response.getWriter();
        out.print("<!DOCTYPE html><html lang=\"en\"><head><meta charset=\"UTF-8\"><title>Bill Payment: Result</title>"
                + "<link rel=\"stylesheet\" href=\"" + context + "/css/default.css\"></head>"
                + "<body><h2>Bill Payment: Result</h2>");
        if ("success".equals(result)) {
            out.print("<h3>Amount Paid with " + paymentType.name() + " Card: "
                    + NumberFormat.getCurrencyInstance().format(value) + "</h3>");
        } else {
            out.print("<h3 style=\"color:red\">Payment failed. Please try again.</h3>");
        }
        out.print("<form action=\"" + context + "/\" method=\"get\"><p><button type=\"submit\">Back</button></p>"
                + "</form></body></html>");
    }
}
