package ru.nikitos.topfive.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
public class Top {
    public enum TopType {
        SONG("Музыка"), VIDEO("Видео"), PHOTO("Фото");

        TopType(String name) {
            this.name = name;
        }

        final String name;

        public String getName() {
            return name;
        }
    }

    private Integer id;
    private String title;
    private String details;
    private TopType type;
}
