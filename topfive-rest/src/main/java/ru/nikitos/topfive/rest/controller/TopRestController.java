package ru.nikitos.topfive.rest.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.nikitos.topfive.rest.controller.payload.UpdateTopPayload;
import ru.nikitos.topfive.entities.Top;
import ru.nikitos.topfive.rest.service.TopService;

import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Objects;

@RestController
@RequestMapping("topfive-api/tops/{topId:\\d+}")
@RequiredArgsConstructor
public class TopRestController {
    private final TopService topService;

    private final MessageSource messageSource;

    @ModelAttribute("top")
    public Top getTop(@PathVariable("topId") int topId) {
        return this.topService.findTop(topId)
                .orElseThrow(() -> new NoSuchElementException("ru.nikitos.msg.top.not_found"));
    }

    @GetMapping
    public Top getTop(@ModelAttribute("top") Top top) {
        return top;
    }

    @PatchMapping
    public ResponseEntity<?> updateTop(@PathVariable("topId") int topId,
                                       @Valid @RequestBody UpdateTopPayload payload,
                                       BindingResult bindingResult) throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) {
                throw exception;
            } else {
                throw new BindException(bindingResult);
            }
        } else {
            this.topService.updateTop(topId, payload.title(), payload.details());
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteTop(@PathVariable("topId") int topId) {
        this.topService.deleteTop(topId);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ProblemDetail> handleNoSuchElementException(NoSuchElementException exception,
                                                                      Locale locale) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.NOT_FOUND,
                Objects.requireNonNull(
                        messageSource.getMessage(exception.getMessage(),new Object[0],exception.getMessage(), locale))
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problemDetail);
    }

}
