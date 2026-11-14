package spring.examples.tutorial.helloservice;

import io.quarkus.runtime.StartupEvent;
import io.vertx.ext.web.Router;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;

@ApplicationScoped
public class HealthRoute {
    void register(@Observes StartupEvent event, Router router) {
        router.get("/helloservice")
                .order(-10_000)
                .handler(context -> context.response()
                        .putHeader("Content-Type", "text/html; charset=UTF-8")
                        .end("<!doctype html><html lang=\"en\"><head><meta charset=\"utf-8\">"
                                + "<title>Hello Service</title></head><body>Hello Service</body></html>"));
    }
}
