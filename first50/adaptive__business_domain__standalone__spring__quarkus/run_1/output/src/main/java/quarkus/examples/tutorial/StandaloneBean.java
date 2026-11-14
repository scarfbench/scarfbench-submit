package quarkus.examples.tutorial;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class StandaloneBean {

    private static final String MESSAGE = "Greetings!";

    public String returnMessage() {
        return MESSAGE;
    }
}
