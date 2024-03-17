package ru.nikitos.topfive.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/main")
public class MainPageController {
    @GetMapping
    public String showMainPage() {
        return "main";
    }
}
