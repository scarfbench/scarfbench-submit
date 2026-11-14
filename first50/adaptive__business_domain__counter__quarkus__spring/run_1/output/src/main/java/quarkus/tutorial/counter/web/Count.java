package quarkus.tutorial.counter.web;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import quarkus.tutorial.counter.ejb.CounterBean;

@Controller
public class Count {
    private final CounterBean counterBean;

    public Count(CounterBean counterBean) {
        this.counterBean = counterBean;
    }

    @GetMapping(value = {"", "/", "/index.xhtml"}, produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
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
