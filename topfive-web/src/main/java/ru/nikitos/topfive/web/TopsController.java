package ru.nikitos.topfive.web;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.nikitos.topfive.client.BadRequestException;
import ru.nikitos.topfive.client.TopRestClient;
import ru.nikitos.topfive.entity.Top;
import ru.nikitos.topfive.web.payload.NewTopPayload;

import java.util.List;

@Controller
@RequestMapping("/tops")
@Slf4j
public class TopsController {
    @Autowired
    private TopRestClient topRestClient;

    @ModelAttribute(name = "topTypes")
    public Enum<Top.TopType>[] topTypes() {
        return Top.TopType.values();
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
