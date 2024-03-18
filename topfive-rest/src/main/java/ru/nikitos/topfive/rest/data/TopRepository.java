package ru.nikitos.topfive.rest.data;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nikitos.topfive.rest.entity.Top;

public interface TopRepository extends JpaRepository<Top, Long> {

}
