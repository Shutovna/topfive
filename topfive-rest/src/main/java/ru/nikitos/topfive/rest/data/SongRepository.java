package ru.nikitos.topfive.rest.data;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nikitos.topfive.entities.Song;

import java.util.List;

public interface SongRepository extends JpaRepository<Song, Integer> {
    public List<Song> findAllByArtistLikeIgnoreCase(String artist);
}
