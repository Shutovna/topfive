package ru.nikitos.topfive.entity;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class Top {

    private Integer id;
    private String title;
    private String details;
    private TopType type;
}
