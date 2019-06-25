package com.protectors.app.protectorsservice.service;

import com.protectors.app.protectorsservice.customexception.SuperheroNotFound;
import com.protectors.app.protectorsservice.entity.Superhero;
import com.protectors.app.protectorsservice.repository.SuperheroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SuperheroService {

    @Autowired
    private SuperheroRepository superheroRepository;

    public Superhero saveOrUpdateSuperhero(Superhero superhero) {
        return superheroRepository.save(superhero);
    }

    public Optional<Superhero> findSuperhero(Long id) {
        return superheroRepository.findById(id);
    }

    public void deleteSuperhero(Long id) {
        Superhero superhero = superheroRepository.findById(id).orElseThrow(() -> new SuperheroNotFound(id));
        superheroRepository.delete(superhero);
    }
}
