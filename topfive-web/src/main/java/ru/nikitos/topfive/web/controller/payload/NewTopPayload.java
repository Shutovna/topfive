package ru.nikitos.topfive.web.controller.payload;

import ru.nikitos.topfive.entities.TopType;

public record NewTopPayload(String title, String details, TopType topType) {
}