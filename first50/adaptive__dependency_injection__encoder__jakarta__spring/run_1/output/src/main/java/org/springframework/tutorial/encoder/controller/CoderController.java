package org.springframework.tutorial.encoder.controller;

import jakarta.tutorial.encoder.Coder;
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

    @PostMapping("/encode")
    public String encode(@Valid @ModelAttribute("coderForm") CoderForm coderForm,
            BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "index";
        }

        coderForm.setCodedString(coder.codeString(coderForm.getInputString(), coderForm.getTransVal()));
        redirectAttributes.addFlashAttribute("coderForm", coderForm);
        return "redirect:/";
    }

    @PostMapping(value = {"/", "/index.xhtml"}, params = "encode")
    public String encodeLegacy(@Valid @ModelAttribute("coderForm") CoderForm coderForm,
            BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        return encode(coderForm, bindingResult, redirectAttributes);
    }

    @PostMapping({"/reset"})
    public String reset(@ModelAttribute("coderForm") CoderForm coderForm,
            RedirectAttributes redirectAttributes) {
        coderForm.setInputString("");
        coderForm.setTransVal(0);
        coderForm.setCodedString(null);
        redirectAttributes.addFlashAttribute("coderForm", coderForm);
        return "redirect:/";
    }

    @PostMapping(value = {"/", "/index.xhtml"}, params = "reset")
    public String resetLegacy(@ModelAttribute("coderForm") CoderForm coderForm,
            RedirectAttributes redirectAttributes) {
        return reset(coderForm, redirectAttributes);
    }
}
