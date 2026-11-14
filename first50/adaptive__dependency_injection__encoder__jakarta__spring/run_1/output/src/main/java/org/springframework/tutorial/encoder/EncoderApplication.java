package org.springframework.tutorial.encoder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"org.springframework.tutorial.encoder", "jakarta.tutorial.encoder"})
public class EncoderApplication {
    public static void main(String[] args) {
        SpringApplication.run(EncoderApplication.class, args);
    }
}
