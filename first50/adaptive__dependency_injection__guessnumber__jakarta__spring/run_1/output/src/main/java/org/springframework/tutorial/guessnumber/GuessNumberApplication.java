package org.springframework.tutorial.guessnumber;
import org.springframework.boot.SpringApplication; import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication(scanBasePackages={"org.springframework.tutorial.guessnumber","jakarta.tutorial.guessnumber"}) public class GuessNumberApplication { public static void main(String[] args){SpringApplication.run(GuessNumberApplication.class,args);} }
