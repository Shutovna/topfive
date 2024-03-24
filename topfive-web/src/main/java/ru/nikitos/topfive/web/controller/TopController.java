package ru.nikitos.topfive.web.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import ru.nikitos.topfive.entities.payload.UpdateTopPayload;
import ru.nikitos.topfive.web.client.BadRequestException;
import ru.nikitos.topfive.entities.Top;
import ru.nikitos.topfive.web.client.TopRestClient;

import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

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
    public String showTop(@ModelAttribute Top top, Model model) {
        log.debug("Showing {}", top);
        List<String> files = top.getItems().stream().map(
                        item -> MvcUriComponentsBuilder.fromMethodName(TopController.class,
                                "serveFile", item.getFileName()
                        ).build().toUri().toString()
                )
                .collect(Collectors.toList());
        model.addAttribute("files", files);
        log.debug("File links: {}", files);
        return "tops/top";
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        /*Resource file = storageService.loadAsResource(filename);
        if (file == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);*/
        return null;
    }

    @GetMapping("edit")
    public String editTop(@ModelAttribute Top top) {
        log.debug("Editing {}", top);
        return "tops/edit_top";
    }

    @PostMapping("edit")
    public String updateTop(@ModelAttribute(binding = false) Top top, @PathVariable Long topId,
                            UpdateTopPayload topPayload, BindingResult bindingResult,
                            Model model
    ) {
        try {
            topRestClient.updateTop(topId, topPayload);
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
