package com.protectors.app.protectorsservice.entity;

import com.fasterxml.jackson.annotation.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "mission")
@ToString
@EqualsAndHashCode
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

    @ManyToMany(mappedBy = "missions", cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @Getter
    @Setter
    @JsonIgnore
    private Set<Superhero> superheroes = new HashSet<>();
}
