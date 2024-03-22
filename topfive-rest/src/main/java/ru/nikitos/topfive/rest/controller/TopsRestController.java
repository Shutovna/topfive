package ru.nikitos.topfive.rest.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ru.nikitos.topfive.rest.controller.payload.NewTopPayload;
import ru.nikitos.topfive.rest.entity.Top;
import ru.nikitos.topfive.rest.service.TopService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("topfive-api/tops")
@RequiredArgsConstructor
@Slf4j
public class TopsRestController {

    private final TopService productService;

    @GetMapping
    public List<Top> findTops(@RequestParam(required = false) String filter) {
        List<Top> tops = this.productService.findAllTops(filter);
        log.debug("Showing {} tops", tops.size());
        return tops;
    }

    @PostMapping
    public ResponseEntity<?> createTop(@Valid @RequestBody NewTopPayload payload,
                                       BindingResult bindingResult,
                                       UriComponentsBuilder uriComponentsBuilder)
            throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) {
                throw exception;
            } else {
                throw new BindException(bindingResult);
            }
        } else {
            Top top = this.productService.createTop(payload.title(), payload.details());
            return ResponseEntity
                    .created(
                            uriComponentsBuilder
                                    .replacePath("/topfive-api/tops/{topId}")
                                    .build(Map.of("topId", top.getId()))
                    ).body(top);
        }
    }
}
