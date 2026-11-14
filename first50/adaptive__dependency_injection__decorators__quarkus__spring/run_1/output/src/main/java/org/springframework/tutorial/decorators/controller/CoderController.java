package org.springframework.tutorial.decorators.controller;

import jakarta.tutorial.decorators.Coder;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public String encode(@Valid @ModelAttribute("coderForm") CoderForm form,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "index";
        }

        form.setCodedString(coder.codeString(form.getInputString(), form.getTransVal()));
        redirectAttributes.addFlashAttribute("coderForm", form);
        return "redirect:/";
    }

    @PostMapping("/reset")
    public String reset(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("coderForm", new CoderForm());
        return "redirect:/";
    }

    @PostMapping({"/", "/index.xhtml"})
    public String submit(@Valid @ModelAttribute("coderForm") CoderForm form,
            BindingResult bindingResult,
            @RequestParam(name = "action", required = false) String action,
            @RequestParam(name = "encodeit:inputString", required = false) String legacyInput,
            @RequestParam(name = "encodeit:transVal", required = false) Integer legacyTransVal,
            @RequestParam(name = "encodeit:submit", required = false) String legacySubmit) {
        if ("reset".equals(action)) {
            form.setInputString("");
            form.setTransVal(0);
            form.setCodedString(null);
            return "index";
        }
        if (legacyInput != null) {
            form.setInputString(legacyInput);
        }
        if (legacyTransVal != null) {
            form.setTransVal(legacyTransVal);
            if (legacyTransVal < 0 || legacyTransVal > 26) {
                bindingResult.rejectValue("transVal", "range", "The value must be between 0 and 26");
            }
        }
        if (bindingResult.hasErrors()) {
            return "index";
        }
        if ("encode".equals(action) || "Encode".equals(legacySubmit) || action == null) {
            form.setCodedString(coder.codeString(form.getInputString(), form.getTransVal()));
        }
        return "index";
    }
}
