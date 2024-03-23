package ru.nikitos.topfive;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import ru.nikitos.topfive.data.UserRepository;
import ru.nikitos.topfive.entities.Genre;
import ru.nikitos.topfive.entity.TopfiveUser;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class TopfiveUserTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindAll() {
        List<TopfiveUser> all = userRepository.findAll();
        assertTrue(all.isEmpty());
    }

    @Test
    public void testCreate() {
        TopfiveUser user = new TopfiveUser("test", "pwd");
        user = userRepository.saveAndFlush(user);

        TopfiveUser userDb = userRepository.findById(user.getId()).orElseThrow();
        assertEquals("test", userDb.getUsername());
        assertEquals("pwd", userDb.getPassword());
        assertNotNull(userDb.getId());
    }

    @Test
    public void testUpdate() {

    }

    @Test
    public void testCreateEmptyFail() {
        Exception exception = assertThrows(ConstraintViolationException.class, () -> {
            userRepository.saveAndFlush(new TopfiveUser());
        });
        String expectedMessage = "Validation failed for classes";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}