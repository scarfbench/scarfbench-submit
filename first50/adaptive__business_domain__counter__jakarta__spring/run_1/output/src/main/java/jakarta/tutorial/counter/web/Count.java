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
package jakarta.tutorial.counter.web;

import jakarta.tutorial.counter.ejb.CounterBean;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ian
 */
@RestController
public class Count {
    private final CounterBean counterBean;

    public Count(CounterBean counterBean) {
        this.counterBean = counterBean;
    }

    @GetMapping(value = {"", "/", "/index.xhtml"}, produces = MediaType.TEXT_HTML_VALUE)
    public String page() {
        int hitCount = counterBean.getHits();
        return """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                    <meta charset="UTF-8">
                    <title>counter - A singleton session bean example.</title>
                    <link rel="stylesheet" href="resources/css/default.css">
                </head>
                <body>
                    <h1>This page has been accessed %d time(s).</h1>
                    <p>Hooray!</p>
                </body>
                </html>
                """.formatted(hitCount);
    }
}
