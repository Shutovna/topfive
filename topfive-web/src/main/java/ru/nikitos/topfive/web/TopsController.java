package ru.nikitos.topfive.web;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.nikitos.topfive.client.TopRestClient;
import ru.nikitos.topfive.entity.Top;
import ru.nikitos.topfive.web.payload.NewTopPayload;

import java.util.List;
import java.util.stream.Stream;

@Controller
@RequestMapping("/tops")
@Slf4j
public class TopsController {
    @Autowired
    private TopRestClient topRestClient;

    @GetMapping("table")
    public String showTops(Model model) {
        List<Top> tops = topRestClient.getAllTops();
        log.debug("Showing {} tops", tops.size());
        model.addAttribute("tops", tops);
        return "tops/top_table";
    }

    @GetMapping("create")
    public String showCreateTop() {
        return "tops/new_top";
    }

    @PostMapping("create")
    public String createTop(@Valid NewTopPayload topPayload, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            Stream<String> errors = bindingResult.getAllErrors().stream().map(
                    ObjectError::getDefaultMessage
            );
            log.warn("Errors in creating top {}", errors);
            model.addAttribute("errors", errors);
            model.addAttribute("top", topPayload);
            return "tops/new_top";
        }

        Top top = topRestClient.createTop(topPayload.title(), topPayload.details());
        log.info("Created {}", top);

        return "redirect:/tops/%d".formatted(top.id());
    }
}
