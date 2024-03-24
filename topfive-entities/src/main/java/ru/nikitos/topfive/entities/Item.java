package ru.nikitos.topfive.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "topfive_seq")
    protected Integer id;

    @NotNull
    protected String title = "title";

    @Lob
    @NotNull
    protected byte[] data;

    @NotNull
    protected String fileName;

    protected String description = "description";

    @ManyToMany(mappedBy = "items")
    protected List<Top> tops = new ArrayList<>();

    @ManyToMany
    protected Set<Rating> ratings = new HashSet<>();

    public Item(@NotNull String title, String description, @NotNull String fileName, @NotNull byte[] data) {
        this.title = title;
        this.description = description;
        this.fileName = fileName;
        this.data = data;
    }
}
