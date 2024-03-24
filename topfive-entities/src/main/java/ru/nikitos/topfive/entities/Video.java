package ru.nikitos.topfive.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class Video extends Item {
    protected String director;

    protected String actors;

    protected Integer releasedYear;

    @ManyToOne
    protected Genre genre;

    @Builder
    public Video(String title, String description, @NotNull String fileName, @NotNull byte[] data,
                 String director, String actors, Integer releasedYear, Genre genre) {
        super(title, description, fileName, data);
        this.director = director;
        this.actors = actors;
        this.releasedYear = releasedYear;
        this.genre = genre;
    }
}
