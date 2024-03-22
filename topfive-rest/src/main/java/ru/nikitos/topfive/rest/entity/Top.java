package ru.nikitos.topfive.rest.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
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
    private String title;

    private String details;

    @ManyToMany
    private Set<Item> items = new HashSet<>();

    @ManyToMany
    protected Set<Rating> ratings = new HashSet<>();

    public Top(String title, String details) {
        this.title = title;
        this.details = details;
    }
}
