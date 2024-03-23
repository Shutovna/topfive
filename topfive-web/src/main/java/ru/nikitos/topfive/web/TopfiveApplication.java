package ru.nikitos.topfive.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "ru.nikitos.topfive.web.entity")
public class TopfiveApplication {
	public static void main(String[] args) {
		SpringApplication.run(TopfiveApplication.class, args);
	}
}
