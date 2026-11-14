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
package jakarta.tutorial.cart.ejb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.tutorial.cart.util.BookException;
import jakarta.tutorial.cart.util.IdVerifier;
import org.springframework.stereotype.Service;

@Service
public class CartBean implements Cart, Serializable {

    String customerId;
    String customerName;
    List<String> contents = new ArrayList<>();

    @Override
    public synchronized void initialize(String person) throws BookException {
        if (person == null) {
            throw new BookException("Null person not allowed.");
        } else {
            customerName = person;
        }

        customerId = "0";
        contents = new ArrayList<>();
    }

    @Override
    public synchronized void initialize(String person, String id) throws BookException {
        if (person == null) {
            throw new BookException("Null person not allowed.");
        } else {
            customerName = person;
        }

        IdVerifier idChecker = new IdVerifier();
        if (idChecker.validate(id)) {
            customerId = id;
        } else {
            throw new BookException("Invalid id: " + id);
        }

        contents = new ArrayList<>();
    }

    @Override
    public synchronized void addBook(String title) {
        contents.add(title);
    }

    @Override
    public synchronized void removeBook(String title) throws BookException {
        boolean result = contents.remove(title);

        if (result == false) {
            throw new BookException("\"" + title + "\" not in cart.");
        }
    }

    @Override
    public synchronized List<String> getContents() {
        return List.copyOf(contents);
    }

    @Override
    public synchronized void remove() {
        contents = new ArrayList<>();
    }
}
