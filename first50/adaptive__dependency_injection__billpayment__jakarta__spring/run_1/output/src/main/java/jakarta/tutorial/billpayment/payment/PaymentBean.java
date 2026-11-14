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
package spring.examples.tutorial.billpayment.payment;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;
import spring.examples.tutorial.billpayment.event.PaymentEvent;
import spring.examples.tutorial.billpayment.interceptor.Logged;

/**
 * Bean that fires DEBIT and CREDIT payment events based on UI selection.
 * Check server log output for event handling output.
 */
@Component("paymentBean")
@SessionScope
public class PaymentBean implements Serializable {

    private static final Logger logger =
            Logger.getLogger(PaymentBean.class.getCanonicalName());
    private static final long serialVersionUID = 7130389273118012929L;

    private transient ApplicationEventPublisher eventPublisher;

    private static final int DEBIT = 1;
    private static final int CREDIT = 2;    
    private int paymentOption = DEBIT;

    @NotNull(message = "An amount is required.")
    @Digits(integer = 10, fraction = 2, message = "Invalid value")
    private BigDecimal value;

    private Date datetime;

    @Autowired
    public void setEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    /**
     * Fires a payment event.
     *
     * @return the response page location
     */
    @Logged
    public String pay() {
        this.setDatetime(Calendar.getInstance().getTime());
        switch (paymentOption) {
            case DEBIT:
                PaymentEvent debitPayload = new PaymentEvent();
                debitPayload.setPaymentType("Debit");
                debitPayload.setValue(value);
                debitPayload.setDatetime(datetime);
                eventPublisher.publishEvent(debitPayload);
                break;
            case CREDIT:
                PaymentEvent creditPayload = new PaymentEvent();
                creditPayload.setPaymentType("Credit");
                creditPayload.setValue(value);
                creditPayload.setDatetime(datetime);
                eventPublisher.publishEvent(creditPayload);
                break;
            default:
                logger.severe("Invalid payment option!");
        }
        return "response";
    }

    /**
     * Resets the values in the index page.
     */
    @Logged
    public void reset() {
        setPaymentOption(DEBIT);
        setValue(BigDecimal.ZERO);
    }

    public int getPaymentOption() {
        return this.paymentOption;
    }

    public void setPaymentOption(int paymentType) {
        this.paymentOption = paymentType;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }
}
