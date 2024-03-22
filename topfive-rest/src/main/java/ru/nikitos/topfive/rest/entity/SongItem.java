package ru.nikitos.topfive.rest.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@ToString(callSuper = true)
public class SongItem extends Item {
    @NotNull
    protected String artist;

    protected Date releasedAt;

    protected Integer bitRate;

    @ManyToOne
    protected Genre genre;

    @Builder
    public SongItem(String artist, String title, String description, @NotNull byte[] data, Date releasedAt, int bitRate, Genre genre) {
        super(title, description, data);
        this.artist = artist;
        this.releasedAt = releasedAt;
        this.bitRate = bitRate;
        this.genre = genre;
    }
}
