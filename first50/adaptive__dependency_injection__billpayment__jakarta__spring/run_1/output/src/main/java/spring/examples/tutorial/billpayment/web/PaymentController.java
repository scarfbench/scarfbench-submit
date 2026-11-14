package spring.examples.tutorial.billpayment.web;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import spring.examples.tutorial.billpayment.payment.PaymentBean;

@Controller
public class PaymentController {
    private final PaymentBean paymentBean;

    public PaymentController(PaymentBean paymentBean) {
        this.paymentBean = paymentBean;
    }

    @ModelAttribute("paymentBean")
    public PaymentBean paymentBean() {
        return paymentBean;
    }

    @GetMapping({"/", "/index", "/index.xhtml"})
    public String index() {
        return "index";
    }

    @PostMapping("/pay")
    public String pay(@Valid @ModelAttribute("paymentBean") PaymentBean form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "index";
        }
        form.pay();
        return "response";
    }

    @PostMapping("/reset")
    public String reset(@ModelAttribute("paymentBean") PaymentBean form) {
        form.reset();
        return "index";
    }

    @GetMapping({"/response", "/response.xhtml"})
    public String response() {
        return "response";
    }
}
