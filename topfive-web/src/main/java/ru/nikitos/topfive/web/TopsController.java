package ru.nikitos.topfive.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.nikitos.topfive.client.TopRestClient;

@Controller
@RequestMapping("/tops")
@Slf4j
public class TopsController {
    @Autowired
    private TopRestClient topService;

    @Autowired
    private MessageSource messageSource;
}
