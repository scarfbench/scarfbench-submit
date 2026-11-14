package org.springframework.tutorial.billpayment.controller;

import jakarta.ws.rs.Path;

/**
 * Exposes the same application beneath the historical deployment context.
 *
 * Quarkus tests exercise application-relative routes such as {@code /pay},
 * while deployed clients use {@code /billpayment/pay}. Inheriting the adapter
 * methods keeps both route sets backed by the same CDI services and behavior.
 */
@Path("/billpayment")
public class BillPaymentContextResource extends PaymentController {
}
