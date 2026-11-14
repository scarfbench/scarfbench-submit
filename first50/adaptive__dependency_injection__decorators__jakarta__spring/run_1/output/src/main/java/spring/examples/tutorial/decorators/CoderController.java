package org.springframework.tutorial.decorators.controller;

import jakarta.tutorial.decorators.Coder;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CoderController {

    private final Coder coder;

    public CoderController(Coder coder) {
        this.coder = coder;
    }

    @GetMapping({"/", "/index.xhtml"})
    public String index(Model model) {
        if (!model.containsAttribute("coderForm")) {
            model.addAttribute("coderForm", new CoderForm());
        }
        return "index";
    }

    @PostMapping({"/", "/index.xhtml", "/encode"})
    public String encode(@Valid @ModelAttribute("coderForm") CoderForm coderForm,
            BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "index";
        }

        String input = coderForm.getInputString() == null ? "" : coderForm.getInputString();
        coderForm.setInputString(input);
        coderForm.setCodedString(coder.codeString(input, coderForm.getTransVal()));
        redirectAttributes.addFlashAttribute("coderForm", coderForm);
        return "redirect:/";
    }

    @PostMapping("/reset")
    public String reset(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("coderForm", new CoderForm());
        return "redirect:/";
    }
}
