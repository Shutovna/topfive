package ru.nikitos.topfive.web.client;

import ru.nikitos.topfive.entities.Song;
import ru.nikitos.topfive.entities.payload.NewSongPayload;
import ru.nikitos.topfive.entities.payload.UpdateSongPayload;

import java.util.List;
import java.util.Optional;

public interface SongRestClient {
    List<Song> findAllSongs();

    Song createSong(NewSongPayload newSongPayload);

    Optional<Song> findSong(Integer songId);

    void updateSong(Integer songId, UpdateSongPayload updateSongPayload);

    void deleteSong(Integer songId);
}
