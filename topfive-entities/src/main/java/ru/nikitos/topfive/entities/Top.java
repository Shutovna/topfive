package ru.nikitos.topfive.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
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
    private TopType type;

    @NotNull
    private String title;

    private String details;

    @ManyToMany
    @JoinTable(name = "top_items",
            joinColumns = @JoinColumn(name = "top_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id"))
    private List<Item> items = new ArrayList<>();


    @ManyToMany
    protected Set<Rating> ratings = new HashSet<>();

    public Top(String title, String details, TopType type) {
        this.title = title;
        this.details = details;
        this.type = type;
    }

    public void addItem(Item item) {
        this.items.add(item);
        item.getTops().add(this);
    }

    public void removeItem(Item item) {
        this.items.remove(item);
        item.getTops().remove(this);
    }
}
