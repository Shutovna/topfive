package ru.nikitos.topfive.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.nikitos.topfive.web.data.AuthorityRepository;
import ru.nikitos.topfive.web.data.UserRepository;
import ru.nikitos.topfive.web.entity.Authority;
import ru.nikitos.topfive.web.entity.TopfiveUser;

import java.util.Optional;

@SpringBootApplication
@EntityScan(basePackages = "ru.nikitos.topfive.web.entity")
@Slf4j
public class TopfiveApplication {

    public static void main(String[] args) {
        SpringApplication.run(TopfiveApplication.class, args);
    }

    @Bean
    CommandLineRunner loadData(AuthorityRepository authorityRepository,
                               UserRepository userRepository,
                               @Value("${topfive.test.username}") String testUserName,
                               @Value("${topfive.test.password}") String testPassword,
                               @Value("${topfive.test.authority}") String testAuthority) {
        return args -> {
            Authority authority = authorityRepository.findByAuthority(testAuthority).orElse(null);
            if (authority == null) {
                authority = authorityRepository.save(new Authority(null, testAuthority));
                log.info("Created {}", authority);
            }
            Optional<TopfiveUser> user = userRepository.findByUsername(testUserName);
            if (user.isEmpty()) {
                TopfiveUser newUser = new TopfiveUser(testUserName, new BCryptPasswordEncoder().encode(testPassword));
                newUser.getAuthorities().add(authority);
                newUser = userRepository.saveAndFlush(newUser);
                log.info("Created {}", newUser);

            }
        };
    }

}
