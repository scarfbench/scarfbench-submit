package spring.examples.tutorial.standalone.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import spring.examples.tutorial.standalone.service.StandaloneService;

@RestController
public class GreetController {
    private final StandaloneService standaloneService;

    public GreetController(StandaloneService standaloneService) {
        this.standaloneService = standaloneService;
    }

    @GetMapping("/")
    public Map<String, String> index() {
        return Map.of("message", standaloneService.returnMessage());
    }

    @GetMapping("/greet")
    public Map<String, String> greet() {
        return Map.of("message", standaloneService.returnMessage());
    }
}
