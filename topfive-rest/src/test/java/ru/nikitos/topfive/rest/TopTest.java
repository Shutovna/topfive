package ru.nikitos.topfive.rest;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.nikitos.topfive.rest.data.TopRepository;
import ru.nikitos.topfive.rest.entity.Top;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class TopTest {
    @Autowired
    TopRepository topRepository;
    @Test
    public void testCreate() {
        Top top = new Top("newTitle", "newDetails");
        top = topRepository.saveAndFlush(top);

        Top topDB = topRepository.findById(top.getId()).orElseThrow();
        assertEquals("newTitle", topDB.getTitle());
        assertEquals("newDetails", topDB.getDetails());
        assertTrue(topDB.getItems().isEmpty());
        assertTrue(topDB.getRatings().isEmpty());
    }

    @Test
    public void testUpdate() {
        Top top = new Top("newTitle", "newDetails");
        top = topRepository.saveAndFlush(top);

        top.setTitle("updatedTitle");
        top.setDetails("updatedDetails");
        topRepository.flush();

        Top topDB = topRepository.findById(top.getId()).orElseThrow();
        assertEquals("updatedTitle", topDB.getTitle());
        assertEquals("updatedDetails", topDB.getDetails());
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
}
