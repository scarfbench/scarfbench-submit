package org.springframework.tutorial.billpayment.controller;

import jakarta.ws.rs.Path;

/**
 * Payment operation adapter used by the Quarkus target contract.
 *
 * The source application exposes its browser form at {@code /pay}; the target
 * Quarkus API groups the same operation below {@code /payment/pay}. Both
 * adapters delegate to the same application-scoped service and therefore fire
 * the same qualified CDI events.
 */
@Path("/payment")
public class PaymentResource extends PaymentController {
}
