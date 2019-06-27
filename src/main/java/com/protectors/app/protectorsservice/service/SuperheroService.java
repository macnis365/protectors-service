package com.protectors.app.protectorsservice.service;

import com.protectors.app.protectorsservice.customexception.SuperheroNotFound;
import com.protectors.app.protectorsservice.entity.Superhero;
import com.protectors.app.protectorsservice.repository.SuperheroRepository;
import com.protectors.app.protectorsservice.utility.CompareUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SuperheroService {

    @Autowired
    private SuperheroRepository superheroRepository;

    public Superhero saveOrUpdateSuperhero(Superhero superhero) {
        return superheroRepository.save(superhero);
    }

    public Superhero findSuperhero(Long id) {
        return superheroRepository.findById(id).orElseThrow(() -> new SuperheroNotFound(id));
    }

    public Superhero updateSuperhero(Long id, Superhero userModifiedSuperhero) {
        return superheroRepository.findById(id).map((Superhero superheroFromDatabase) -> {
                    updatePersistedSuperhero(userModifiedSuperhero, superheroFromDatabase);
                    CompareUtility.updatePersistedMissions(userModifiedSuperhero, superheroFromDatabase);
                    return superheroRepository.save(superheroFromDatabase);
                }
        ).orElseThrow(() -> new SuperheroNotFound(id));

    }

    private void updatePersistedSuperhero(Superhero userModifiedSuperhero, Superhero superheroFromDatabase) {
        superheroFromDatabase.setFirstName(userModifiedSuperhero.getFirstName());
        superheroFromDatabase.setLastName(userModifiedSuperhero.getLastName());
        superheroFromDatabase.setSuperheroName(userModifiedSuperhero.getSuperheroName());
    }

    public void deleteSuperhero(Long id) {
        Superhero superhero = superheroRepository.findById(id).orElseThrow(() -> new SuperheroNotFound(id));
        superheroRepository.delete(superhero);
    }
}
