package ru.nikitos.topfive.web.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(schema = "user_management")
public class Authority {
    @Id
    @SequenceGenerator(name = "topfive_seq", sequenceName = "common_sequence", initialValue = 1000, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "topfive_seq")
    private Integer id;

    @NotNull
    @Column(unique = true, updatable = false)
    private String authority;
}
