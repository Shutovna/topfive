package ru.nikitos.topfive.rest.data;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nikitos.topfive.entities.VideoItem;

public interface VideoItemRepository extends JpaRepository<VideoItem, Integer> {
}
