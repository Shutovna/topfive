package ru.nikitos.topfive.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Genre {
    @Id
    @SequenceGenerator(name = "topfive_seq", sequenceName = "common_sequence", initialValue = 1000, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "topfive_seq")
    private Integer id;

    @NotNull
    private String name;

}
