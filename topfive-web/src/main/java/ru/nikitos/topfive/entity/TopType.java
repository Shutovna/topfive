package ru.nikitos.topfive.entity;

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
