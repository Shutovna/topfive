package ru.nikitos.topfive.web.payload;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateTopPayload(String title, String details) {}