package com.protectors.app.protectorsservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "mission")
public class Mission implements Serializable {
    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @NotEmpty(message = "mission name must not be empty")
    @NotNull(message = "mission name cannot not be null")
    private String name;

    @Getter
    @Setter
    private boolean isCompleted;

    @Getter
    @Setter
    private boolean isDeleted;

    @ManyToMany(mappedBy = "missions", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Getter
    @Setter
    @JsonIgnore
    private Set<Superhero> superheroes = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Mission)) return false;

        Mission mission = (Mission) o;

        return id != null ? id.equals(mission.id) : mission.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Mission{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", isCompleted=" + isCompleted +
                ", isDeleted=" + isDeleted +
                '}';
    }


    public static class MissionBuilder {
        private Long id;
        private String name;
        private boolean completed;
        private boolean deleted;

        public MissionBuilder() {
        }

        public MissionBuilder setId(Long id) {
            this.id = id;
            return this;
        }

        public MissionBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public MissionBuilder setCompleted(boolean completed) {
            this.completed = completed;
            return this;
        }

        public MissionBuilder setDeleted(boolean deleted) {
            this.deleted = deleted;
            return this;
        }

        public Mission build() {
            Mission mission = new Mission();
            mission.setId(id);
            mission.setName(name);
            mission.setCompleted(completed);
            mission.setDeleted(deleted);
            return mission;
        }

    }
}
