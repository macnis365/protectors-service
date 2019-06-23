package com.protectors.app.protectorsservice.repository;

import com.protectors.app.protectorsservice.entity.Superhero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SuperheroRepository extends JpaRepository<Superhero, Integer> {
}
