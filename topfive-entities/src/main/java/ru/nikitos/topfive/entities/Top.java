package ru.nikitos.topfive.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Top {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "topfive_seq")
    private Integer id;

    @NotNull
    private TopType type;

    @NotNull
    private String title;

    private String details;

    @ManyToMany
    private Set<Item> items = new HashSet<>();

    @ManyToMany
    protected Set<Rating> ratings = new HashSet<>();

    public Top(String title, String details, TopType type) {
        this.title = title;
        this.details = details;
        this.type = type;
    }
}
