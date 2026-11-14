package org.springframework.tutorial.encoder.controller;

import jakarta.tutorial.encoder.Coder;
import jakarta.validation.Valid;
import java.util.Map;
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

    public CoderController(Coder coder) { this.coder = coder; }

    @GetMapping({"/", "/index.xhtml"})
    public String index(Model model) {
        if (!model.containsAttribute("coderForm")) {
            model.addAttribute("coderForm", new CoderForm());
        }
        return "index";
    }

    @PostMapping("/encode")
    public String encode(@Valid @ModelAttribute("coderForm") CoderForm form,
                         BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) return "index";
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
    public String legacyFacesSubmit(@RequestParam Map<String, String> parameters,
                                    Model model) {
        if (parameters.keySet().stream().anyMatch(name -> name.toLowerCase().contains("reset"))) {
            model.addAttribute("coderForm", new CoderForm());
            return "index";
        }
        CoderForm form = new CoderForm();
        form.setInputString(first(parameters, "encodeit:inputString", "inputString"));
        try {
            form.setTransVal(Integer.valueOf(first(parameters, "encodeit:transVal", "transVal")));
        } catch (NumberFormatException ex) {
            form.setTransVal(null);
        }
        if (form.getTransVal() == null || form.getTransVal() < 0 || form.getTransVal() > 26) {
            model.addAttribute("validationError", "The value must be between 0 and 26.");
        } else {
            form.setCodedString(coder.codeString(form.getInputString(), form.getTransVal()));
        }
        model.addAttribute("coderForm", form);
        return "index";
    }

    private String first(Map<String, String> parameters, String preferred, String fallback) {
        return parameters.getOrDefault(preferred, parameters.getOrDefault(fallback, ""));
    }
}
