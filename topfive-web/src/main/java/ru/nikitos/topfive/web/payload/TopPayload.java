package ru.nikitos.topfive.web.payload;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TopPayload(
        @NotNull(message = "{ru.nikitos.msg.top.title.not_null}")
        @Size(min = 4, max = 50, message = "{ru.nikitos.msg.top.title.size}")
        String title,
        String details) {

}