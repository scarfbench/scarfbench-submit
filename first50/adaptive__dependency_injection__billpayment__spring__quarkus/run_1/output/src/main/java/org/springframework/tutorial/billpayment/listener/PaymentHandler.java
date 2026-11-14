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
package org.springframework.tutorial.billpayment.listener;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.tutorial.billpayment.aspect.Logged;
import org.springframework.tutorial.billpayment.event.PaymentEvent;
import org.springframework.tutorial.billpayment.payment.Credit;
import org.springframework.tutorial.billpayment.payment.Debit;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;

/**
 * Handler for PaymentEvent.
 */
@Logged
@ApplicationScoped
public class PaymentHandler {

    private static final Logger logger = Logger.getLogger(PaymentHandler.class.getCanonicalName());

    public PaymentHandler() {
        logger.log(Level.INFO, "PaymentHandler created.");
    }

    public void creditPayment(@Observes @Credit PaymentEvent event) {
        logger.log(Level.INFO, "PaymentHandler - Credit Handler: {0}", event.toString());
    }

    public void debitPayment(@Observes @Debit PaymentEvent event) {
        logger.log(Level.INFO, "PaymentHandler - Debit Handler: {0}", event.toString());
    }
}
