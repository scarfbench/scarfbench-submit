/*
 * Copyright (c), Eclipse Foundation, Inc. and its licensors.
 * SPDX-License-Identifier: BSD-3-Clause
 */
package jakarta.tutorial.cart.web;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;

import io.quarkus.runtime.StartupEvent;
import io.vertx.ext.web.Router;

/** Redirects the context root to the static service landing page. */
@ApplicationScoped
public class RootRoute {

    void register(@Observes StartupEvent event, Router router) {
        router.getWithRegex("^/cart$").handler(context -> context.response()
                    .setStatusCode(308)
                    .putHeader("Location", "/cart/")
                    .end());
    }
}
