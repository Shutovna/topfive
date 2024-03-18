package ru.nikitos.topfive.service;

import ru.nikitos.topfive.entity.Top;

import java.util.List;
import java.util.Optional;

public interface TopService {
    List<Top> getAllTops();

    Top createTop(String title, String details);

    Optional<Top> getTop(Long topId);

    Top updateTop(Long topId, String title, String details);

    void deleteTop(Long topId);
}
