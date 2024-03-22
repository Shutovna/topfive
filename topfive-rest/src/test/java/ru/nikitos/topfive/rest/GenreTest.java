package ru.nikitos.topfive.rest;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.nikitos.topfive.rest.data.GenreRepository;
import ru.nikitos.topfive.rest.entity.Genre;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class GenreTest {
    @Autowired
    GenreRepository genreRepository;

    @Test
    public void testFindAll() {
        List<Genre> all = genreRepository.findAll();
        assertEquals(4, all.size());
    }

    @Test
    public void testCreate() {
        Genre genre = new Genre(null, "New genre");
        Genre genreDB = genreRepository.saveAndFlush(genre);
        assertEquals("New genre", genreDB.getName());
        assertNotNull(genreDB.getId());
        assertTrue(genreDB.getId() >= 1000);
    }

    @Test
    public void testUpdate() {
        Genre genre = new Genre(null, "New genre");
        Genre genreDB = genreRepository.saveAndFlush(genre);
        genreDB.setName("genre2");
        genreDB = genreRepository.saveAndFlush(genre);
        assertEquals("genre2", genreDB.getName());
    }

    @Test
    public void testCreateEmptyFail() {
        Exception exception = assertThrows(ConstraintViolationException.class, () -> {
            genreRepository.saveAndFlush(new Genre());
        });
        String expectedMessage = "Validation failed for classes";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
