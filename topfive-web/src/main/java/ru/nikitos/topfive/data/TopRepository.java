package ru.nikitos.topfive.data;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nikitos.topfive.entity.Top;

public interface TopRepository extends JpaRepository<Top, Long> {

}
