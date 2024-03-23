package ru.nikitos.topfive.web;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import ru.nikitos.topfive.web.data.AuthorityRepository;
import ru.nikitos.topfive.web.entity.Authority;
import ru.nikitos.topfive.web.entity.TopfiveUser;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class AuthorityTest {
    @Autowired
    AuthorityRepository authorityRepository;

    @Test
    public void testFindAll() {
        List<Authority> all = authorityRepository.findAll();
        assertEquals(1, all.size());
        assertEquals("ROLE_USER", all.get(0).getAuthority());
    }

    @Test
    public void testCreate() {
        Authority auth = authorityRepository.saveAndFlush(new Authority(null, "TEST_AUTHORITY"));
        Authority authDB = authorityRepository.findById(auth.getId()).orElseThrow();
        assertEquals("TEST_AUTHORITY", authDB.getAuthority());
    }

    @Test
    public void testFailUnique() {
        Exception exception = assertThrows(DataIntegrityViolationException.class, () -> {
            Authority auth = authorityRepository.saveAndFlush(new Authority(null, "TEST_AUTHORITY"));
            Authority auth2 = authorityRepository.saveAndFlush(new Authority(null, "TEST_AUTHORITY"));

        });
        String expectedMessage = "could not execute statement";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
