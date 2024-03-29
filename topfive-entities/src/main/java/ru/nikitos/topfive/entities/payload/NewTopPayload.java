package ru.nikitos.topfive.entities.payload;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import ru.nikitos.topfive.entities.TopType;

public record NewTopPayload(
        @NotNull(message = "{ru.nikitos.msg.top.title.not_null}")
        @Size(min = 4, max = 50, message = "{ru.nikitos.msg.top.title.size}")
        String title,
        String details,
        @NotNull
        TopType topType) {

}