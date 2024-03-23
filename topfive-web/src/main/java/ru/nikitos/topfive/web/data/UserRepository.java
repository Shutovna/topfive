package ru.nikitos.topfive.web.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.nikitos.topfive.web.entity.TopfiveUser;

import java.util.Optional;

public interface UserRepository extends JpaRepository<TopfiveUser, Long> {
    Optional<TopfiveUser> findByUsername(String username) throws UsernameNotFoundException;
}