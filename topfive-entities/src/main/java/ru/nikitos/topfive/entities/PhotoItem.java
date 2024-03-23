package ru.nikitos.topfive.entities;

import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Builder
public class PhotoItem extends Item {
    protected String modelName;
}
