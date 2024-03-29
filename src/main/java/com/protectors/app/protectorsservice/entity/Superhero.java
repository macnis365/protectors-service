package com.protectors.app.protectorsservice.entity;

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
    @NotNull(message = "superhero name cannot not be null")
    private String superheroName;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "superhero_missions",
            joinColumns = {@JoinColumn(name = "superhero_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "mission_id", referencedColumnName = "id")})
    @Getter
    @Setter
    private Set<Mission> missions = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Superhero)) return false;

        Superhero superhero = (Superhero) o;

        if (id != null ? !id.equals(superhero.id) : superhero.id != null) return false;
        if (firstName != null ? !firstName.equals(superhero.firstName) : superhero.firstName != null) return false;
        if (lastName != null ? !lastName.equals(superhero.lastName) : superhero.lastName != null) return false;
        if (superheroName != null ? !superheroName.equals(superhero.superheroName) : superhero.superheroName != null)
            return false;
        return missions != null ? missions.equals(superhero.missions) : superhero.missions == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (superheroName != null ? superheroName.hashCode() : 0);
        result = 31 * result + (missions != null ? missions.hashCode() : 0);
        return result;
    }

    public static class SuperheroBuilder {
        private Long id;
        private String firstName;
        private String lastName;
        private String superheroName;
        private Set<Mission> missions = new HashSet<>();

        public SuperheroBuilder setId(Long id) {
            this.id = id;
            return this;
        }

        public SuperheroBuilder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public SuperheroBuilder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public SuperheroBuilder setSuperheroName(String superheroName) {
            this.superheroName = superheroName;
            return this;
        }

        public SuperheroBuilder setMissions(Set<Mission> missions) {
            this.missions = missions;
            return this;
        }

        public Superhero build() {
            Superhero superhero = new Superhero();
            superhero.setId(id);
            superhero.setFirstName(firstName);
            superhero.setLastName(lastName);
            superhero.setSuperheroName(superheroName);
            superhero.getMissions().addAll(missions);
            return superhero;
        }
    }
}