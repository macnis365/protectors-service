package com.protectors.app.protectorsservice.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
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
    private String superheroName;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "superhero_missions",
            joinColumns = {@JoinColumn(name = "superhero_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "mission_id", referencedColumnName = "id")})
    @JsonManagedReference
    private Set<Mission> missions = new HashSet<>();

}