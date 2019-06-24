package com.protectors.app.protectorsservice.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "mission")
public class Mission implements Serializable {
    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private boolean isCompleted;

    @Getter
    @Setter
    private boolean isDeleted;

    /*@OneToMany(mappedBy = "missions", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonBackReference
    @Getter
    @Setter
    private Set<Superhero> superheroes = new HashSet<>();*/
}
