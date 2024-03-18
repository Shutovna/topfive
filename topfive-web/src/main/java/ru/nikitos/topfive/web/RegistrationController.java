package ru.nikitos.topfive.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import ru.nikitos.topfive.service.UserAlreadyExistsException;
import ru.nikitos.topfive.service.UserService;
import ru.nikitos.topfive.web.payload.UserPayload;

import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/register")
@Slf4j
@RequiredArgsConstructor
public class RegistrationController {
    @Autowired
    private UserService userService;

    @Autowired
    private MessageSource messageSource;

    @GetMapping
    public String showRegPage() {
        return "registration";
    }

    @PostMapping
    public String registerUser(@Valid UserPayload payload, BindingResult bindingResult, Model model, Locale locale) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("payload", payload);
            List<String> errors = bindingResult.getFieldErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .toList();
            model.addAttribute("errors", errors);
            return "registration";

        }
        try {
            userService.saveUser(payload.getUsername(), payload.getPassword());
            log.info("Saved: {} ", payload);

        } catch (UserAlreadyExistsException e) {
            model.addAttribute("errors",
                    messageSource.getMessage("ru.nikitos.msg.user_exists", new String[]{payload.getUsername()}, locale)
            );
            log.error(e.getMessage());
            return "registration";
        }

        return "redirect:/main";
    }

}