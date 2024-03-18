package ru.nikitos.topfive.web;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.nikitos.topfive.entity.Top;
import ru.nikitos.topfive.service.TopService;
import ru.nikitos.topfive.web.payload.TopPayload;

import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Stream;

@Controller
@RequestMapping("/tops")
@Slf4j
public class TopController {
    @Autowired
    private TopService topService;

    @Autowired
    private MessageSource messageSource;

    @GetMapping("table")
    public String showTops(Model model) {
        List<Top> tops = topService.getAllTops();
        log.debug("Showing {} tops", tops.size());
        model.addAttribute("tops", tops);
        return "tops/top_table";
    }

    @GetMapping("create")
    public String showCreateTop() {
        return "tops/new_top";
    }

    @GetMapping("{topId}")
    public String showTop(@PathVariable Long topId, Model model) {
        Optional<Top> top = topService.getTop(topId);
        log.debug("Showing {}", top);
        model.addAttribute("top",
                top.orElseThrow(() -> new NoSuchElementException("ru.nikitos.msg.top.not_found"))
        );
        return "tops/top";
    }

    @GetMapping("{topId}/edit")
    public String editTop(@PathVariable Long topId, Model model) {
        Optional<Top> top = topService.getTop(topId);
        log.debug("Editing {}", top);
        model.addAttribute("top",
                top.orElseThrow(() -> new NoSuchElementException("ru.nikitos.msg.top.not_found"))
        );
        return "tops/edit";
    }

    @PostMapping("create")
    public String createTop(@Valid TopPayload topPayload, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            Stream<String> errors = bindingResult.getAllErrors().stream().map(
                    DefaultMessageSourceResolvable::getDefaultMessage
            );
            log.warn("Errors in creating top {}", errors);
            model.addAttribute("errors", errors);
            model.addAttribute("top", topPayload);
            return "tops/new_top";
        }

        Top top = topService.createTop(topPayload.title(), topPayload.details());
        log.info("Created {}", top);

        return "redirect:/tops/%d".formatted(top.getId());
    }

    @PostMapping("{topId}/edit")
    public String updateTop(@PathVariable Long topId,
                            @Valid TopPayload topPayload, BindingResult bindingResult,
                            Model model
    ) {
        if (bindingResult.hasErrors()) {
            Stream<String> errors = bindingResult.getAllErrors().stream().map(
                    DefaultMessageSourceResolvable::getDefaultMessage
            );
            log.warn("Errors in updating top {}", errors);
            model.addAttribute("errors", errors);
            model.addAttribute("top", topPayload);
            return "tops/edit";
        }

        Top top = topService.updateTop(topId, topPayload.title(), topPayload.details());
        log.info("Updated {}", top);

        return "redirect:/tops/%d".formatted(top.getId());
    }

    @PostMapping("{topId}/delete")
    public String deleteTop(@PathVariable Long topId, Model model) {
        Optional<Top> top = topService.getTop(topId);
        log.debug("Deleting {}", top);
        model.addAttribute("top",
                top.orElseThrow(() -> new NoSuchElementException("ru.nikitos.msg.top.not_found"))
        );
        topService.deleteTop(topId);
        return "redirect:/tops/table";
    }

    @ExceptionHandler(NoSuchElementException.class)
    public String handleNoSuchElementException(NoSuchElementException exception,
                                               Model model, HttpServletResponse response, Locale locale) {
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        model.addAttribute("error",
                messageSource.getMessage(exception.getMessage(), new Object[0], exception.getMessage(), locale)
        );
        return "errors/404";
    }
}
