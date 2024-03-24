package ru.nikitos.topfive.web.client;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import ru.nikitos.topfive.entities.Song;
import ru.nikitos.topfive.entities.payload.NewSongPayload;
import ru.nikitos.topfive.entities.payload.UpdateSongPayload;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class DefaultSongRestClient implements SongRestClient {

    public static final ParameterizedTypeReference<List<Song>> SONG_TYPE_REFERENCE =
            new ParameterizedTypeReference<>() {
            };
    private final RestClient restClient;

    public DefaultSongRestClient(RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public List<Song> findAllSongs() {
        return this.restClient
                .get()
                .uri("/topfive-api/songs")
                .retrieve()
                .body(SONG_TYPE_REFERENCE);
    }

    @Override
    public Song createSong(NewSongPayload newSongPayload) {
        try {
            return this.restClient
                    .post()
                    .uri("/topfive-api/songs")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(newSongPayload)
                    .retrieve()
                    .body(Song.class);
        } catch (HttpClientErrorException.BadRequest exception) {
            ProblemDetail problemDetail = exception.getResponseBodyAs(ProblemDetail.class);
            throw new BadRequestException((List<String>) problemDetail.getProperties().get("errors"));
        }
    }

    @Override
    public Optional<Song> findSong(Integer songId) {
        try {
            return Optional.ofNullable(
                    this.restClient.get()
                            .uri("/topfive-api/songs/{songId}", songId)
                            .retrieve()
                            .body(Song.class));
        } catch (HttpClientErrorException.NotFound exception) {
            return Optional.empty();
        }
    }

    @Override
    public void updateSong(Integer songId, UpdateSongPayload updateSongPayload) {
        try {
            this.restClient
                    .patch()
                    .uri("/topfive-api/songs/{songId}", songId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(updateSongPayload)
                    .retrieve()
                    .toBodilessEntity();
        } catch (HttpClientErrorException.BadRequest exception) {
            ProblemDetail problemDetail = exception.getResponseBodyAs(ProblemDetail.class);
            throw new BadRequestException((List<String>) problemDetail.getProperties().get("errors"));
        }

    }

    @Override
    public void deleteSong(Integer songId) {
        try {
            this.restClient
                    .delete()
                    .uri("/topfive-api/songs/{songId}", songId)
                    .retrieve()
                    .toBodilessEntity();
        } catch (HttpClientErrorException.NotFound exception) {
            throw new NoSuchElementException(exception);
        }
    }
}
