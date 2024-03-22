package ru.nikitos.topfive.web.payload;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import ru.nikitos.topfive.entity.Top;

public record NewTopPayload(String title, String details, Top.TopType topType) {
}