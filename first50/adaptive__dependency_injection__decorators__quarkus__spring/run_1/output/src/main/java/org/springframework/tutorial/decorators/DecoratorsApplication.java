package org.springframework.tutorial.decorators;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"org.springframework.tutorial.decorators", "jakarta.tutorial.decorators"})
public class DecoratorsApplication {
    public static void main(String[] args) {
        SpringApplication.run(DecoratorsApplication.class, args);
    }
}
