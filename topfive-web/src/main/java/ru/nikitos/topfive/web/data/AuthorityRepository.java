package ru.nikitos.topfive.web.data;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nikitos.topfive.web.entity.Authority;

import java.util.Optional;

public interface AuthorityRepository extends JpaRepository<Authority, Integer> {
    Optional<Authority> findByAuthority(String authority);
}
