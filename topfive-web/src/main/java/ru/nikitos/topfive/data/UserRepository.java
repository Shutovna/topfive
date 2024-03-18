package ru.nikitos.topfive.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.nikitos.topfive.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username) throws UsernameNotFoundException;
}