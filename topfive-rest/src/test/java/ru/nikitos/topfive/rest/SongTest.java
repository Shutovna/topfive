package ru.nikitos.topfive.rest;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.core.io.Resource;
import ru.nikitos.topfive.entities.Song;
import ru.nikitos.topfive.rest.data.GenreRepository;
import ru.nikitos.topfive.rest.data.SongRepository;
import ru.nikitos.topfive.entities.Genre;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
public class SongTest {
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private SongRepository songRepository;
    @Autowired
    private GenreRepository genreRepository;

    @Value("classpath:example_file.xml")
    private Resource mp3Example;

    private String fileName = "example_file.xml";

    @Test
    public void testWithEM() throws IOException {
        Genre genre = entityManager.find(Genre.class, 1);
        byte[] mp3AsByteArray = new byte[0];
        Song song = Song.builder()
                .artist("Metallica").title("Unforgiven").description("testDescription")
                .genre(genre)
                .fileName(fileName)
                .data(mp3AsByteArray)
                .releasedAt(new Date())
                .build();
        entityManager.persist(song);
        entityManager.flush();
    }

    @Test
    public void testItemRepositoryCreate() throws IOException {
        Genre genre = genreRepository.findByName("Metall");
        byte[] mp3AsByteArray = mp3Example.getContentAsByteArray();
        Date releasedAt = new Date();

        Song song = Song.builder()
                .artist("Metallica").title("Unforgiven").description("testDescription")
                .genre(genre)
                .fileName(fileName)
                .data(mp3AsByteArray)
                .releasedAt(releasedAt)
                .bitRate(312)
                .build();
        song = songRepository.save(song);

        Song song2 = Song.builder()
                .artist("Iron Maiden").title("Number of the beast").description("testDescription2")
                .genre(genre)
                .fileName(fileName)
                .data(mp3AsByteArray)
                .build();
        song2 = songRepository.save(song2);

        List<Song> all = songRepository.findAll();
        assertEquals(2, all.size());

        Song songDb = songRepository.findById(song.getId()).orElseThrow();

        assertTrue(songDb.getId() >= 1000);
        assertEquals("Metallica", songDb.getArtist());
        assertEquals("Unforgiven", songDb.getTitle());
        assertEquals(genre, songDb.getGenre());
        assertEquals("testDescription", songDb.getDescription());
        assertEquals("example_file.xml", songDb.getFileName());
        assertArrayEquals(mp3AsByteArray, songDb.getData());
        assertEquals(312, songDb.getBitRate());
        assertEquals(releasedAt, songDb.getReleasedAt());
        assertTrue(songDb.getRatings().isEmpty());

        Song songDb2 = songRepository.findById(song2.getId()).orElseThrow();
        assertTrue(songDb2.getId() > 1000);
        assertEquals("Iron Maiden", songDb2.getArtist());
        assertEquals("Number of the beast", songDb2.getTitle());
        assertEquals(genre, songDb2.getGenre());
        assertEquals("testDescription2", songDb2.getDescription());
        assertEquals("example_file.xml", songDb2.getFileName());
        assertArrayEquals(mp3AsByteArray, songDb2.getData());
        assertTrue(songDb2.getRatings().isEmpty());
    }

    @Test
    public void testUpdate() throws IOException {
        Genre genre = genreRepository.findByName("Metall");
        byte[] mp3AsByteArray = mp3Example.getContentAsByteArray();
        Date releasedAt = new Date();

        Song song = Song.builder()
                .artist("Metallica").title("Unforgiven").description("testDescription")
                .genre(genre)
                .fileName(fileName)
                .data(mp3AsByteArray)
                .releasedAt(releasedAt)
                .bitRate(192)
                .build();
        song = songRepository.saveAndFlush(song);

        song.setArtist("newArtist");
        song.setTitle("newTitle");
        song.setDescription("newDescription");
        song.setFileName("newFilename");
        mp3AsByteArray[0] = 55;
        song.setData(mp3AsByteArray);
        Genre genre2 = genreRepository.findByName("Classic");
        song.setGenre(genre2);
        song.setBitRate(300);
        releasedAt.setTime(releasedAt.getTime() + 1);
        song.setReleasedAt(releasedAt);

        Song songDB = songRepository.saveAndFlush(song);
        assertEquals("newArtist", songDB.getArtist());
        assertEquals("newTitle", songDB.getTitle());
        assertEquals("newDescription", songDB.getDescription());
        assertEquals("newFilename", songDB.getFileName());
        assertArrayEquals(mp3AsByteArray, songDB.getData());
        assertEquals(genre2, songDB.getGenre());
        assertEquals(300, songDB.getBitRate());
        assertEquals(releasedAt, songDB.getReleasedAt());

    }

    @Test
    public void testFailsWhenEmptySong() {
        Exception exception = assertThrows(ConstraintViolationException.class, () -> {
            Song song = new Song();
            songRepository.saveAndFlush(song);
        });
        String expectedMessage = "Validation failed for classes";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testFailsWhenNoData() {
        Exception exception = assertThrows(ConstraintViolationException.class, () -> {
            Genre genre = genreRepository.findByName("Metall");
            Song song = Song.builder()
                    .artist("Metallica").title("Unforgiven")
                    .description("testDescription").genre(genre)
                    .build();
            songRepository.saveAndFlush(song);
        });

        String expectedMessage = "Validation failed for classes";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }
}
