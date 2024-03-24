package ru.nikitos.topfive.rest;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.core.io.Resource;
import ru.nikitos.topfive.entities.Video;
import ru.nikitos.topfive.rest.data.GenreRepository;
import ru.nikitos.topfive.rest.data.VideoItemRepository;
import ru.nikitos.topfive.entities.Genre;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class VideoItemTest {
    @Autowired
    VideoItemRepository videoItemRepository;

    @Autowired
    GenreRepository genreRepository;

    @Value("classpath:example_file.xml")
    private Resource exampleFile;

    private String fileName = "example_file.xml";

    @Test
    public void testFindNone() {
        assertTrue(videoItemRepository.findAll().isEmpty());
    }

    @Test
    public void testCreate() throws IOException {
        Genre genre = genreRepository.findById(1).orElseThrow();
        byte[] contentAsByteArray = exampleFile.getContentAsByteArray();

        Video video = getVideo(genre, contentAsByteArray);
        videoItemRepository.saveAndFlush(video);

        List<Video> videos = videoItemRepository.findAll();
        assertEquals(1, videos.size());

        Video videoDB = videos.get(0);
        assertEquals("Gladiator", videoDB.getTitle());
        assertEquals("best film", videoDB.getDescription());
        assertEquals("Crow", videoDB.getActors());
        assertEquals("Scott", videoDB.getDirector());
        assertEquals(2000, videoDB.getReleasedYear());
        assertEquals(genre, videoDB.getGenre());
        assertArrayEquals(contentAsByteArray, videoDB.getData());
    }

    @Test
    public void testUpdate() throws IOException {
        Genre genre = genreRepository.findById(1).orElseThrow();
        byte[] contentAsByteArray = exampleFile.getContentAsByteArray();
        Video video = getVideo(genre, contentAsByteArray);
        videoItemRepository.saveAndFlush(video);

        Video videoItem = videoItemRepository.findAll().get(0);
        videoItem.setActors("newActors");
        videoItem.setDirector("newDirector");
        Genre genre2 = genreRepository.findById(2).orElseThrow();
        videoItem.setGenre(genre2);
        videoItem.setReleasedYear(2012);
        contentAsByteArray[0] = 55;
        videoItem.setFileName("file.mp3");
        videoItem.setData(contentAsByteArray);
        videoItem.setDescription("newDescription");
        videoItem = videoItemRepository.saveAndFlush(videoItem);

        assertEquals("newActors", videoItem.getActors());
        assertEquals("newDirector", videoItem.getDirector());
        assertEquals(genre2, videoItem.getGenre());
        assertEquals(2012, videoItem.getReleasedYear());
        assertEquals("file.mp3", videoItem.getFileName());
        assertArrayEquals(contentAsByteArray, videoItem.getData());
        assertEquals("newDescription", videoItem.getDescription());
    }

    private Video getVideo(Genre genre, byte[] contentAsByteArray) {
        return Video.builder()
                .title("Gladiator").description("best film")
                .actors("Crow").director("Scott")
                .releasedYear(2000)
                .genre(genre)
                .fileName(fileName)
                .data(contentAsByteArray)
                .build();
    }

    @Test
    public void testFailsWhenEmptySong() {
        Exception exception = assertThrows(ConstraintViolationException.class, () -> {
            Video song = new Video();
            videoItemRepository.saveAndFlush(song);
        });
        String expectedMessage = "Validation failed for classes";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testFailsWhenEmptyBaseClassSong() {
        Exception exception = assertThrows(ConstraintViolationException.class, () -> {
            Video song = Video.builder()
                    .actors("test").director("director")
                    .releasedYear(2000)
                    .genre(genreRepository.findById(1).orElseThrow())
                    .build();
            videoItemRepository.saveAndFlush(song);
        });
        String expectedMessage = "Validation failed for classes";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
}
