package ru.nikitos.topfive.rest;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.core.io.Resource;
import ru.nikitos.topfive.rest.data.GenreRepository;
import ru.nikitos.topfive.rest.data.SongItemRepository;
import ru.nikitos.topfive.rest.entity.Genre;
import ru.nikitos.topfive.rest.entity.SongItem;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
public class SongItemTest {
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private SongItemRepository songItemRepository;
    @Autowired
    private GenreRepository genreRepository;

    @Value("classpath:example_file.xml")
    private Resource mp3Example;

    @Test
    public void testWithEM() throws IOException {
        Genre genre = entityManager.find(Genre.class, 1);
        SongItem song = SongItem.builder()
                .artist("Metallica").title("Unforgiven").description("testDescription")
                .genre(genre)
                .data(mp3Example.getContentAsByteArray())
                .build();
        entityManager.persist(song);
        entityManager.flush();
    }

    @Test
    public void testItemRepositoryCreate() throws IOException {
        Genre genre = genreRepository.findByName("Metall");
        byte[] mp3AsByteArray = mp3Example.getContentAsByteArray();

        SongItem song = SongItem.builder()
                .artist("Metallica").title("Unforgiven").description("testDescription")
                .genre(genre)
                .data(mp3AsByteArray)
                .build();
        song = songItemRepository.save(song);

        SongItem song2 = SongItem.builder()
                .artist("Iron Maiden").title("Number of the beast").description("testDescription2")
                .genre(genre)
                .data(mp3AsByteArray)
                .build();
        song2 = songItemRepository.save(song2);

        List<SongItem> all = songItemRepository.findAll();
        assertEquals(2, all.size());

        SongItem songDb = songItemRepository.findById(song.getId()).orElseThrow();

        assertTrue(songDb.getId() >= 1000);
        assertEquals("Metallica", songDb.getArtist());
        assertEquals("Unforgiven", songDb.getTitle());
        assertEquals(genre, songDb.getGenre());
        assertEquals("testDescription", songDb.getDescription());
        assertArrayEquals(mp3AsByteArray, songDb.getData());
        assertTrue(songDb.getRatings().isEmpty());

        SongItem songDb2 = songItemRepository.findById(song2.getId()).orElseThrow();
        assertTrue(songDb2.getId() > 1000);
        assertEquals("Iron Maiden", songDb2.getArtist());
        assertEquals("Number of the beast", songDb2.getTitle());
        assertEquals(genre, songDb2.getGenre());
        assertEquals("testDescription2", songDb2.getDescription());
        assertArrayEquals(mp3AsByteArray, songDb2.getData());
        assertTrue(songDb2.getRatings().isEmpty());
    }

    @Test
    public void testFailsWhenEmptySong() {
        Exception exception = assertThrows(ConstraintViolationException.class, () -> {
            SongItem song = new SongItem();
            songItemRepository.saveAndFlush(song);
        });
        String expectedMessage = "Validation failed for classes";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testFailsWhenNoData() {
        Exception exception = assertThrows(ConstraintViolationException.class, () -> {
            Genre genre = genreRepository.findByName("Metall");
            SongItem song = SongItem.builder()
                    .artist("Metallica").title("Unforgiven")
                    .description("testDescription").genre(genre)
                    .build();
            songItemRepository.saveAndFlush(song);
        });

        String expectedMessage = "Validation failed for classes";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }
}
