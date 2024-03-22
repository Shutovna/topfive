package ru.nikitos.topfive.rest.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class VideoItem extends Item {
    protected String director;

    protected String actors;

    protected Integer releasedYear;

    @ManyToOne
    protected Genre genre;

    @Builder
    public VideoItem(String title, String description, @NotNull byte[] data, String director, String actors, Integer releasedYear, Genre genre) {
        super(title, description, data);
        this.director = director;
        this.actors = actors;
        this.releasedYear = releasedYear;
        this.genre = genre;
    }
}
