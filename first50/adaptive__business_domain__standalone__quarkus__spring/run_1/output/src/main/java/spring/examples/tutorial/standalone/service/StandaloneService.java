package spring.examples.tutorial.standalone.service;

import org.springframework.stereotype.Service;

@Service
public class StandaloneService {
    private static final String MESSAGE = "Greetings!";

    public String returnMessage() {
        return MESSAGE;
    }
}
