package ru.nikitos.topfive.client;

import ru.nikitos.topfive.entity.Top;

import java.util.List;
import java.util.Optional;

public interface TopRestClient {
    List<Top> findAllTops(String filter);

    Top createTop(String title, String details);

    Optional<Top> findTop(Long topId);

    void updateTop(Long topId, String title, String details);

    void deleteTop(Long topId);
}
