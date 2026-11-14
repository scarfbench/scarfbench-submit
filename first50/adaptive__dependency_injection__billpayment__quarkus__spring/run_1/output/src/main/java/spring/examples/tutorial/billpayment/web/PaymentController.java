package spring.examples.tutorial.billpayment.web;

import java.math.BigDecimal;
import java.math.RoundingMode;

import jakarta.tutorial.billpayment.payment.PaymentBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PaymentController {
    private final PaymentBean paymentBean;

    public PaymentController(PaymentBean paymentBean) {
        this.paymentBean = paymentBean;
    }

    @GetMapping({"", "/", "/index", "/index.xhtml"})
    public String index(Model model) {
        addPaymentModel(model);
        return "index";
    }

    @PostMapping({"/index", "/index.xhtml"})
    public String submit(@RequestParam(value = "paymentForm:amt", required = false) String legacyAmount,
            @RequestParam(value = "amount", required = false) String amount,
            @RequestParam(value = "paymentForm:opt", required = false) Integer legacyOption,
            @RequestParam(value = "paymentOption", required = false) Integer paymentOption,
            @RequestParam(value = "paymentForm:submit", required = false) String legacySubmit,
            @RequestParam(value = "action", required = false) String action, Model model) {
        if ("Reset".equalsIgnoreCase(action) || "Reset".equalsIgnoreCase(legacySubmit)) {
            paymentBean.reset();
            return "redirect:/index.xhtml";
        }
        String submittedAmount = legacyAmount != null ? legacyAmount : amount;
        Integer submittedOption = legacyOption != null ? legacyOption : paymentOption;
        if (submittedAmount == null || submittedAmount.isBlank()) {
            return showError("An amount is required.", model);
        }
        try {
            BigDecimal value = new BigDecimal(submittedAmount);
            if (value.precision() - value.scale() > 10 || Math.max(value.scale(), 0) > 2) {
                throw new NumberFormatException();
            }
            paymentBean.setValue(value);
            paymentBean.setPaymentOption(submittedOption == null ? 1 : submittedOption);
            paymentBean.pay();
            addPaymentModel(model);
            return "response";
        } catch (NumberFormatException ex) {
            return showError("Invalid value", model);
        }
    }

    private String showError(String error, Model model) {
        model.addAttribute("error", error);
        addPaymentModel(model);
        return "index";
    }

    @GetMapping({"/response", "/response.xhtml"})
    public String response(Model model) {
        addPaymentModel(model);
        return "response";
    }

    private void addPaymentModel(Model model) {
        model.addAttribute("amount", paymentBean.getValue());
        model.addAttribute("paymentOption", paymentBean.getPaymentOption());
        model.addAttribute("amountDisplay", paymentBean.getValue() == null ? "$0.00"
                : "$" + paymentBean.getValue().setScale(2, RoundingMode.UNNECESSARY).toPlainString());
    }

    @PostMapping("/reset")
    public String reset() {
        paymentBean.reset();
        return "redirect:/index.xhtml";
    }
}
