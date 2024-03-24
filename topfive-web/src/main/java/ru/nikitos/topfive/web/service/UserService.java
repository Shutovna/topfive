package ru.nikitos.topfive.web.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.nikitos.topfive.web.data.AuthorityRepository;
import ru.nikitos.topfive.web.data.UserRepository;
import ru.nikitos.topfive.web.entity.Authority;
import ru.nikitos.topfive.web.entity.TopfiveUser;

import java.util.Arrays;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(topfiveUser -> User.builder()
                        .username(topfiveUser.getUsername())
                        .password(topfiveUser.getPassword())
                        .authorities(topfiveUser.getAuthorities().stream()
                                .map(Authority::getAuthority)
                                .map(SimpleGrantedAuthority::new)
                                .toList())
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException("Cant find user with username: " + username));
    }

    public void saveUser(String username, String password) throws UserAlreadyExistsException {
        Optional<TopfiveUser> topfiveUserFromDB = userRepository.findByUsername(username);
        if (topfiveUserFromDB.isEmpty()) {
            TopfiveUser topfiveUser = new TopfiveUser(username, bCryptPasswordEncoder.encode(password));
            Authority authority = authorityRepository.findByAuthority("ROLE_USER").orElseThrow(
                    IllegalStateException::new
            );
            topfiveUser.setAuthorities(Arrays.asList(authority));
            userRepository.save(topfiveUser);

        } else {
            throw new UserAlreadyExistsException("User %s already exists".formatted(topfiveUserFromDB));
        }
    }

    public boolean deleteUser(Long userId) {
        if (userRepository.findById(userId).isPresent()) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }
}