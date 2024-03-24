package ru.nikitos.topfive.web.client;

import ru.nikitos.topfive.entities.Top;
import ru.nikitos.topfive.entities.TopType;
import ru.nikitos.topfive.entities.payload.NewTopPayload;
import ru.nikitos.topfive.entities.payload.UpdateTopPayload;

import java.util.List;
import java.util.Optional;

public interface TopRestClient {
    List<Top> findAllTops(String filter);

    Top createTop(NewTopPayload newTopPayload);

    Optional<Top> findTop(Long topId);

    void updateTop(Long topId, UpdateTopPayload updateTopPayload);

    void deleteTop(Long topId);
}
