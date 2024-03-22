package ru.nikitos.topfive.web;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import ru.nikitos.topfive.client.BadRequestException;
import ru.nikitos.topfive.entity.Top;
import ru.nikitos.topfive.client.TopRestClient;
import ru.nikitos.topfive.web.payload.UpdateTopPayload;

import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

@Controller
@RequestMapping("tops/{topId:\\d+}")
@Slf4j
public class TopController {
    @Autowired
    private TopRestClient topRestClient;

    @Autowired
    private MessageSource messageSource;

    @ModelAttribute("top")
    private Top getTop(@PathVariable Long topId) {
        return topRestClient.findTop(topId).orElseThrow(
                () -> new NoSuchElementException("ru.nikitos.msg.top.not_found")
        );
    }

    @GetMapping
    public String showTop(@ModelAttribute Top top) {
        log.debug("Showing {}", top);
        return "tops/top";
    }

    @GetMapping("edit")
    public String editTop(@ModelAttribute Top top) {
        log.debug("Editing {}", top);
        return "tops/edit_top";
    }

    @PostMapping("edit")
    public String updateTop(@ModelAttribute(binding = false) Top top, @PathVariable Long topId,
                            @Valid UpdateTopPayload topPayload, BindingResult bindingResult,
                            Model model
    ) {
        try {
            topRestClient.updateTop(topId, topPayload.title(), topPayload.details());
            log.info("Updated top {}", topId);
            return "redirect:/tops/%d".formatted(topId);
        } catch (BadRequestException e) {
            log.warn("Errors in updating top {}", e.getErrors());
            model.addAttribute("errors", e.getErrors());
            model.addAttribute("payload", topPayload);
            return "tops/edit_top";

        }
    }

    @PostMapping("delete")
    public String deleteTop(@ModelAttribute Top top, @PathVariable Long topId, Model model) {
        topRestClient.deleteTop(topId);
        log.info("Top {} deleted", top);
        return "redirect:/tops/table";
    }

    @ExceptionHandler(NoSuchElementException.class)
    public String handleNoSuchElementException(NoSuchElementException exception,
                                               Model model, HttpServletResponse response, Locale locale) {
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        model.addAttribute("error",
                messageSource.getMessage(exception.getMessage(), new Object[0], exception.getMessage(), locale)
        );
        log.debug(exception.getLocalizedMessage());
        return "errors/404";
    }
}
