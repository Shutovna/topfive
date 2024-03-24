package ru.nikitos.topfive.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nikitos.topfive.entities.Song;
import ru.nikitos.topfive.entities.payload.NewSongPayload;
import ru.nikitos.topfive.entities.payload.UpdateSongPayload;
import ru.nikitos.topfive.rest.data.SongRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class DefaultSongService implements SongService {

    @Autowired
    private SongRepository songRepository;

    @Override
    public List<Song> findAllSongs() {
        return songRepository.findAll();
    }

    @Override
    public Song createSong(NewSongPayload payload) {
        return songRepository.save(Song.builder()
                .artist(payload.getArtist())
                .title(payload.getTitle())
                .description(payload.getDescription())
                .bitRate(payload.getBitRate())
                .releasedAt(payload.getReleasedAt())
                .fileName(payload.getFileName())
                .data(payload.getData())
                .build());
    }

    @Override
    public Optional<Song> findSong(Integer SongId) {
        return songRepository.findById(SongId);
    }

    @Override
    public Song updateSong(Integer SongId, UpdateSongPayload payload) {
        songRepository.findById(SongId)
                .map(song -> {
                    song.setArtist(payload.getArtist());
                    song.setTitle(payload.getTitle());
                    song.setDescription(payload.getDescription());
                    song.setBitRate(payload.getBitRate());
                    song.setReleasedAt(payload.getReleasedAt());
                    song.setFileName(payload.getFileName());
                    song.setData(payload.getData());
                    return songRepository.save(song);
                });
        throw new NoSuchElementException();
    }

    @Override
    public void deleteSong(Integer SongId) {
        songRepository.deleteById(SongId);
    }
}
