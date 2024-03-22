package ru.nikitos.topfive.rest.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

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
    protected String title;

    @Lob
    @NotNull
    protected byte[] data;

    protected String description;

    @ManyToMany
    protected Set<Rating> ratings = new HashSet<>();

    public Item(@NotNull String title, String description, @NotNull byte[] data) {
        this.title = title;
        this.description = description;
        this.data = data;
    }
}
