package ru.nikitos.topfive.rest;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.nikitos.topfive.entities.*;
import ru.nikitos.topfive.rest.data.SongRepository;
import ru.nikitos.topfive.rest.data.TopRepository;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class TopTest {
    @Autowired
    TopRepository topRepository;
    @Autowired
    SongRepository songRepository;

    @Test
    public void testCreate() {
        Top top = new Top("newTitle", "newDetails", TopType.SONG);
        top = topRepository.saveAndFlush(top);

        Top topDB = topRepository.findById(top.getId()).orElseThrow();
        assertEquals("newTitle", topDB.getTitle());
        assertEquals("newDetails", topDB.getDetails());
        assertEquals(TopType.SONG, topDB.getType());
        assertTrue(topDB.getItems().isEmpty());
        assertTrue(topDB.getRatings().isEmpty());
    }

    @Test
    public void testUpdate() {
        Top top = new Top("newTitle", "newDetails", TopType.VIDEO);
        top = topRepository.saveAndFlush(top);

        top.setTitle("updatedTitle");
        top.setDetails("updatedDetails");
        top.setType(TopType.PHOTO);
        topRepository.flush();

        Top topDB = topRepository.findById(top.getId()).orElseThrow();
        assertEquals("updatedTitle", topDB.getTitle());
        assertEquals("updatedDetails", topDB.getDetails());
        assertEquals(TopType.PHOTO, topDB.getType());
    }

    @Test
    public void testFailsWhenNoData() {
        Exception exception = assertThrows(ConstraintViolationException.class, () -> {
            Top top = new Top();
            topRepository.saveAndFlush(top);
        });
        String expectedMessage = "Validation failed for classes";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testFailsWhenNoType() {
        Exception exception = assertThrows(ConstraintViolationException.class, () -> {
            Top top = new Top(null, null, "title", null, null, null);
            topRepository.saveAndFlush(top);
        });
        String expectedMessage = "Validation failed for classes";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testAddRemoveItems() {
        Top top = new Top("newTitle", "newDetails", TopType.SONG);
        Song song = new Song("artist", "title", "desc",
                "file.txt", new byte[0], null, null, null);
        top.addItem(song);

        song = songRepository.save(song);
        top = topRepository.saveAndFlush(top);

        Top topDB = topRepository.findById(top.getId()).orElseThrow();
        assertEquals(song, topDB.getItems().get(0));

        topDB.removeItem(song);
        topRepository.saveAndFlush(topDB);
        topDB = topRepository.findById(topDB.getId()).orElseThrow();
        assertTrue(topDB.getItems().isEmpty());
        assertTrue(song.getTops().isEmpty());
    }
}
