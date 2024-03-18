package ru.nikitos.topfive.web.payload;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record NewTopPayload(String title, String details) {
}