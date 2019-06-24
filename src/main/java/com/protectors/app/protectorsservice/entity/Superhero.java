package com.protectors.app.protectorsservice.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "superhero")
@ToString
@EqualsAndHashCode
public class Superhero implements Serializable {

    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    private String firstName;

    @Getter
    @Setter
    private String lastName;

    @Getter
    @Setter
    @NotEmpty(message = "superhero name must not be empty")
    @NotNull()
    private String superheroName;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "superhero_missions",
            joinColumns = {@JoinColumn(name = "superhero_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "mission_id", referencedColumnName = "id")})
    @Getter
    @Setter
 private Set<Mission> missions = new HashSet<>();

}