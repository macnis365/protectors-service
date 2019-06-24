package com.protectors.app.protectorsservice.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "Superhero")
public class Superhero {

    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @Column(name = "FirstName")
    private String firstName;

    @Getter
    @Setter
    @Column(name = "LastName")
    private String lastName;

    @Getter
    @Setter
    @Column(name = "SuperheroName")
    private String superheroName;

}
