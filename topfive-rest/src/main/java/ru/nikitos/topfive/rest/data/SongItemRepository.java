package ru.nikitos.topfive.rest.data;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nikitos.topfive.entities.SongItem;

import java.util.List;

public interface SongItemRepository extends JpaRepository<SongItem, Integer> {
    public List<SongItem> findAllByArtistLikeIgnoreCase(String artist);
}
