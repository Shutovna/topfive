package ru.nikitos.topfive.entities.payload;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class NewSongPayload {
    @NotNull(message = "{ru.nikitos.msg.song.artist.not_null}")
    @Size(min = 4, max = 50, message = "{ru.nikitos.msg.song.artist.size}")
    String artist;
    @NotNull(message = "{ru.nikitos.msg.song.title.not_null}")
    @Size(min = 4, max = 50, message = "{ru.nikitos.msg.song.title.size}")
    String title;
    String description;
    Integer bitRate;
    Date releasedAt;
    @NotNull(message = "{ru.nikitos.msg.song.fileName.not_null}")
    String fileName;
    @NotNull
    byte[] data;
}