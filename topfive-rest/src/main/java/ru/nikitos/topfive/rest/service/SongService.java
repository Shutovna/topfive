package ru.nikitos.topfive.rest.service;

import org.hibernate.sql.Update;
import ru.nikitos.topfive.entities.Song;
import ru.nikitos.topfive.entities.payload.NewSongPayload;
import ru.nikitos.topfive.entities.payload.UpdateSongPayload;

import java.util.List;
import java.util.Optional;

public interface SongService {
    List<Song> findAllSongs();

    Song createSong(NewSongPayload payload);

    Optional<Song> findSong(Integer songId);

    Song updateSong(Integer songId, UpdateSongPayload payload);

    void deleteSong(Integer songId);
}
