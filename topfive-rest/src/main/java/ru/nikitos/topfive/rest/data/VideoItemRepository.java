package ru.nikitos.topfive.rest.data;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nikitos.topfive.entities.Video;

public interface VideoItemRepository extends JpaRepository<Video, Integer> {
}
