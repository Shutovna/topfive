package ru.nikitos.topfive.web;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import ru.nikitos.topfive.web.data.AuthorityRepository;
import ru.nikitos.topfive.web.data.UserRepository;
import ru.nikitos.topfive.web.entity.Authority;
import ru.nikitos.topfive.web.entity.TopfiveUser;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class TopfiveUserTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

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
        assertTrue(userDb.getAuthorities().isEmpty());
    }

    @Test
    public void testWithAuthority() {
        TopfiveUser user = new TopfiveUser("test2", "pwd2");
        Authority authority = authorityRepository.findAll().get(0);
        user.getAuthorities().add(authority);
        user = userRepository.saveAndFlush(user);

        TopfiveUser userDb = userRepository.findById(user.getId()).orElseThrow();
        assertEquals("test2", userDb.getUsername());
        assertEquals("pwd2", userDb.getPassword());
        assertNotNull(userDb.getId());
        assertEquals(authority, userDb.getAuthorities().get(0));
    }

    @Test
    public void testFailWithTwoAuthorities() {
        Exception exception = assertThrows(DataIntegrityViolationException.class, () -> {
            TopfiveUser user = new TopfiveUser("test", "pwd");
            List<Authority> authorities = new ArrayList<>();
            Authority authority = authorityRepository.findAll().get(0);
            authorities.add(authority);
            authorities.add(authority);
            user.setAuthorities(authorities);
            userRepository.saveAndFlush(user);
        });
        String expectedMessage = "could not execute statement";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
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