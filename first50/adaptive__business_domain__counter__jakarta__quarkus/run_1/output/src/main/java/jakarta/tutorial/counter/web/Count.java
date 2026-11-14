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

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.tutorial.counter.ejb.CounterBean;

/**
 *
 * @author ian
 */
@Path("/")
public class Count {
    @Inject
    CounterBean counterBean;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public String get() {
        return renderPage();
    }

    @GET
    @Path("index.xhtml")
    @Produces(MediaType.TEXT_HTML)
    public String getIndex() {
        return renderPage();
    }

    private String renderPage() {
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
