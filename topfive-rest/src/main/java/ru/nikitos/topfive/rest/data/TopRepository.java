package ru.nikitos.topfive.rest.data;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nikitos.topfive.rest.entity.Top;

import java.util.List;

public interface TopRepository extends JpaRepository<Top, Integer> {

    List<Top> findAllByTitleLikeIgnoreCase(String filter);

}
