package ru.nikitos.topfive.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.nikitos.topfive.data.UserRepository;
import ru.nikitos.topfive.entity.User;

public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Cant find user with username: " + username);
        }
        return user;
    }

    public void saveUser(String username, String password) throws UserAlreadyExistsException {
        User userFromDB = userRepository.findByUsername(username);
        if(userFromDB != null) {
            throw new UserAlreadyExistsException("User %s already exists".formatted(userFromDB));
        }

        User user = new User(username, bCryptPasswordEncoder.encode(password));
        user.setRole("ROLE_USER");
        userRepository.save(user);
    }

    public boolean deleteUser(Long userId) {
        if (userRepository.findById(userId).isPresent()) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }
}