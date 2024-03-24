package ru.nikitos.topfive.rest.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ru.nikitos.topfive.entities.Song;
import ru.nikitos.topfive.entities.payload.NewSongPayload;
import ru.nikitos.topfive.entities.payload.NewTopPayload;
import ru.nikitos.topfive.entities.Top;
import ru.nikitos.topfive.rest.service.SongService;
import ru.nikitos.topfive.rest.service.TopService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("topfive-api/songs")
@RequiredArgsConstructor
@Slf4j
public class SongsRestController {
    private final SongService songService;

    @GetMapping
    public List<Song> findSongs() {
        List<Song> songs = this.songService.findAllSongs();
        log.debug("Showing {} songs", songs.size());
        return songs;
    }

    @PostMapping
    public ResponseEntity<?> createSong(@Valid @RequestBody NewSongPayload payload,
                                        BindingResult bindingResult,
                                        UriComponentsBuilder uriComponentsBuilder)
            throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) {
                throw exception;
            } else {
                throw new BindException(bindingResult);
            }
        } else {
            Song song = this.songService.createSong(payload);
            return ResponseEntity
                    .created(
                            uriComponentsBuilder
                                    .replacePath("/topfive-api/songs/{songId}")
                                    .build(Map.of("songId", song.getId()))
                    ).body(song);
        }
    }
}
