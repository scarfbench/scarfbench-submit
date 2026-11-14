package spring.examples.tutorial.counter.controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import spring.examples.tutorial.counter.service.CounterService;

@Path("/")
public class CountController {
    private final CounterService counterService;

    @Inject
    public CountController(CounterService counterService) {
        this.counterService = counterService;
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public String index() {
        int hitCount = counterService.getHits();
        return """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                    <meta charset="UTF-8">
                    <title>Counter - A singleton session bean example.</title>
                    <link rel="stylesheet" href="/counter/css/default.css">
                </head>
                <body>
                    <h1>This page has been accessed %d time(s).</h1>
                    <p>Hooray!</p>
                </body>
                </html>
                """.formatted(hitCount);
    }
}
