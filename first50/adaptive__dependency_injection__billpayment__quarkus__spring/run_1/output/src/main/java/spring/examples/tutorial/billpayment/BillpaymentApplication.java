package spring.examples.tutorial.billpayment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"spring.examples.tutorial.billpayment", "jakarta.tutorial.billpayment"})
public class BillpaymentApplication {
    public static void main(String[] args) {
        SpringApplication.run(BillpaymentApplication.class, args);
    }
}
