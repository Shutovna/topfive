package ru.nikitos.topfive.web.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.nikitos.topfive.web.client.BadRequestException;
import ru.nikitos.topfive.web.client.TopRestClient;
import ru.nikitos.topfive.entities.Top;
import ru.nikitos.topfive.entities.TopType;
import ru.nikitos.topfive.web.controller.payload.NewTopPayload;

import java.util.List;

@Controller
@RequestMapping("/tops")
@Slf4j
public class TopsController {
    @Autowired
    private TopRestClient topRestClient;

    @ModelAttribute(name = "topTypes")
    public Enum<TopType>[] topTypes() {
        return TopType.values();
    }

    @GetMapping("table")
    public String showTops(Model model, @RequestParam(value = "filter", required = false) String filter) {
        List<Top> tops = topRestClient.findAllTops(filter);
        log.debug("Showing {} tops", tops.size());
        model.addAttribute("tops", tops);
        model.addAttribute("filter", filter);
        return "tops/top_table";
    }

    @GetMapping("create")
    public String showCreateTop(Model model) {
        return "tops/new_top";
    }

    @PostMapping("create")
    public String createTop(@Valid NewTopPayload topPayload, Model model) {
        try {
            Top top = topRestClient.createTop(topPayload.title(), topPayload.details(), topPayload.topType());
            log.info("Created {}", top);
            return "redirect:/tops/%d".formatted(top.getId());
        } catch (BadRequestException e) {
            model.addAttribute("errors", e.getErrors());
            model.addAttribute("top", topPayload);
            return "tops/new_top";
        }
    }
}
