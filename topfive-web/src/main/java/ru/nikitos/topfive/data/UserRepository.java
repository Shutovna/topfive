package ru.nikitos.topfive.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.nikitos.topfive.entity.TopfiveUser;

import java.util.Optional;

public interface UserRepository extends CrudRepository<TopfiveUser, Long> {
    Optional<TopfiveUser> findByUsername(String username) throws UsernameNotFoundException;
}