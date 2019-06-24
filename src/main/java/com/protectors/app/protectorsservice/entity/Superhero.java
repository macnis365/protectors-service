package com.protectors.app.protectorsservice.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "superhero")
public class Superhero implements Serializable {

    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
/*
    @Column(name = "FirstName")
*/
    private String firstName;

    @Getter
    @Setter
/*
    @Column(name = "LastName")
*/
    private String lastName;

    @Getter
    @Setter
/*
    @Column(name = "SuperheroName")
*/
    @NotEmpty(message = "superhero name must not be empty")
    private String superheroName;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "superhero_missions",
            joinColumns = {@JoinColumn(name = "superhero_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "mission_id", referencedColumnName = "id")})
/*
    @JsonManagedReference
*/
    @Getter
    @Setter
/*
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
*/

    private Set<Mission> missions = new HashSet<>();

}