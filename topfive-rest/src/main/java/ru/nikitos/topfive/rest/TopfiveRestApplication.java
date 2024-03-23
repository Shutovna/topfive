package ru.nikitos.topfive.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = {"ru.nikitos.topfive.entities","ru.nikitos.topfive.rest.entity"})
public class TopfiveRestApplication {

    public static void main(String[] args) {
        SpringApplication.run(TopfiveRestApplication.class, args);
    }

}
