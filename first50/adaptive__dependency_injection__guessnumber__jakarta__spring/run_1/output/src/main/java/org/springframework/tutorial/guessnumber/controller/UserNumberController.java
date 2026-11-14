package org.springframework.tutorial.guessnumber.controller;

import jakarta.tutorial.guessnumber.UserNumberBean;
import jakarta.tutorial.guessnumber.Generator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("userNumberBean")
public class UserNumberController {
    private final Generator generator;

    public UserNumberController(Generator generator) {
        this.generator = generator;
    }

    @ModelAttribute("userNumberBean")
    public UserNumberBean userNumberBean() {
        return new UserNumberBean(generator);
    }

    @GetMapping({"/", "/index.xhtml"})
    public String index(
            @ModelAttribute("userNumberBean") UserNumberBean userNumberBean,
            Model model) {
        populate(userNumberBean, model);
        return "index";
    }

    @PostMapping({"/guess", "/", "/index.xhtml"})
    public String guess(
            @ModelAttribute("userNumberBean") UserNumberBean userNumberBean,
            BindingResult errors,
            Model model) {
        Integer guess = userNumberBean.getUserNumber();
        if (guess == null) {
            errors.rejectValue("userNumber", "required", "A number is required");
        } else if (guess < userNumberBean.getMinimum() || guess > userNumberBean.getMaximum()) {
            errors.rejectValue("userNumber", "range", "Invalid guess");
        }
        if (errors.hasErrors()) {
            populate(userNumberBean, model);
            return "index";
        }
        userNumberBean.check();
        return "redirect:/";
    }

    @PostMapping("/reset")
    public String reset(@ModelAttribute("userNumberBean") UserNumberBean userNumberBean) {
        userNumberBean.reset();
        return "redirect:/";
    }

    private void populate(UserNumberBean userNumberBean, Model model) {
        model.addAttribute("minimum", userNumberBean.getMinimum());
        model.addAttribute("maximum", userNumberBean.getMaximum());
        model.addAttribute("remainingGuesses", userNumberBean.getRemainingGuesses());
        model.addAttribute("targetNumber", userNumberBean.getNumber());
        model.addAttribute("currentGuess", userNumberBean.getUserNumber());
        model.addAttribute("message", userNumberBean.getMessage());
    }
}
