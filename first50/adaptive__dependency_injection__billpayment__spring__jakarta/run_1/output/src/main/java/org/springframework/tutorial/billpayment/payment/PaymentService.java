/*
 * Copyright (c), Eclipse Foundation, Inc. and its licensors.
 *
 * All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v1.0, which is available at
 * https://www.eclipse.org/org/documents/edl-v10.php
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */
package org.springframework.tutorial.billpayment.payment;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;

import org.springframework.tutorial.billpayment.aspect.Logged;
import org.springframework.tutorial.billpayment.event.PaymentEvent;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;

/**
 * Service that publishes DEBIT and CREDIT payment events.
 * Check server log output for event handling output.
 */
@ApplicationScoped
public class PaymentService {

    private static final Logger logger = Logger.getLogger(PaymentService.class.getCanonicalName());

    @Inject
    @Debit
    private Event<PaymentEvent> debitEvents;

    @Inject
    @Credit
    private Event<PaymentEvent> creditEvents;

    /**
     * Processes a payment and publishes a payment event.
     *
     * @param paymentType the type of payment (DEBIT or CREDIT)
     * @param value       the payment amount
     * @return success message
     */
    @Logged
    public String processPayment(PaymentType paymentType, BigDecimal value) {
        Date datetime = Calendar.getInstance().getTime();

        switch (paymentType) {
            case DEBIT -> {
                PaymentEvent debitPayload = new PaymentEvent("Debit", value, datetime);
                debitEvents.fire(debitPayload);
            }
            case CREDIT -> {
                PaymentEvent creditPayload = new PaymentEvent("Credit", value, datetime);
                creditEvents.fire(creditPayload);
            }
            default -> {
                logger.severe("Invalid payment option!");
                return "error";
            }
        }
        return "success";
    }
}
