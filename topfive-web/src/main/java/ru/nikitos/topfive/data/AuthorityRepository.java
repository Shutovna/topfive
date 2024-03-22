package ru.nikitos.topfive.data;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nikitos.topfive.entity.Authority;

import java.util.Optional;

public interface AuthorityRepository extends JpaRepository<Authority, Integer> {
    Optional<Authority> findByAuthorityEquals(String authority);
}
