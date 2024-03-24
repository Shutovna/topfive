package ru.nikitos.topfive.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@ToString(callSuper = true)
public class Song extends Item {
    @NotNull
    protected String artist="artist";

    protected Date releasedAt = new Date();

    protected Integer bitRate = 321;

    @ManyToOne
    protected Genre genre;

    @Builder
    public Song(String artist, String title, String description, @NotNull String fileName, @NotNull byte[] data,
                Date releasedAt, Integer bitRate, Genre genre) {
        super(title, description, fileName, data);
        this.artist = artist;
        this.releasedAt = releasedAt;
        this.bitRate = bitRate;
        this.genre = genre;
    }
}
