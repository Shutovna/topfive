package ru.nikitos.topfive.rest.data;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nikitos.topfive.entities.Genre;

public interface GenreRepository extends JpaRepository<Genre, Integer> {
    Genre findByName(String metall);
}
