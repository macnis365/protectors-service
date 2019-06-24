package com.protectors.app.protectorsservice.service;

import com.protectors.app.protectorsservice.entity.Superhero;
import com.protectors.app.protectorsservice.repository.SuperheroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SuperheroService {

    @Autowired
    private SuperheroRepository superheroRepository;

    public Long createSuperhero(Superhero superhero) {
        return superheroRepository.save(superhero).getId();
    }
}
