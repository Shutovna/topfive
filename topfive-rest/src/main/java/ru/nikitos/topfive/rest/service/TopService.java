package ru.nikitos.topfive.rest.service;

import ru.nikitos.topfive.entities.Top;
import ru.nikitos.topfive.entities.TopType;

import java.util.List;
import java.util.Optional;

public interface TopService {
    List<Top> findAllTops(String filter);

    Top createTop(String title, String details, TopType topType);

    Optional<Top> findTop(Integer topId);

    Top updateTop(Integer topId, String title, String details);

    void deleteTop(Integer topId);
}
