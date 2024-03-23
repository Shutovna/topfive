package ru.nikitos.topfive.web.payload;

import ru.nikitos.topfive.entity.TopType;

public record NewTopPayload(String title, String details, TopType topType) {
}