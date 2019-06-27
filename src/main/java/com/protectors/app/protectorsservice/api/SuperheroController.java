package com.protectors.app.protectorsservice.api;

import com.protectors.app.protectorsservice.customexception.SuperheroNotFound;
import com.protectors.app.protectorsservice.entity.Superhero;
import com.protectors.app.protectorsservice.service.SuperheroService;
import com.protectors.app.protectorsservice.utility.CompareUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/superhero")
public class SuperheroController {

    @Autowired
    private SuperheroService superheroService;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Superhero createSuperhero(@Valid @RequestBody Superhero superhero) {
        return superheroService.saveOrUpdateSuperhero(superhero);
    }

    @GetMapping("{id}")
    public Superhero fetchSuperhero(@PathVariable final Long id) {
        return superheroService.findSuperhero(id).orElseThrow(() -> new SuperheroNotFound(id));
    }

    @PutMapping("{id}")
    public Superhero amendSuperhero(@Valid @RequestBody Superhero userModifiedSuperhero, @PathVariable final Long id) {
        return superheroService.findSuperhero(id).map((Superhero superheroFromDatabase) -> {
                    updatePersistedSuperhero(userModifiedSuperhero, superheroFromDatabase);
                    CompareUtility.updatePersistedMissions(userModifiedSuperhero, superheroFromDatabase);
                    return superheroService.saveOrUpdateSuperhero(superheroFromDatabase);
                }
        ).orElseThrow(() -> new SuperheroNotFound(id));
    }

    private void updatePersistedSuperhero(Superhero userModifiedSuperhero, Superhero superheroFromDatabase) {
        superheroFromDatabase.setFirstName(userModifiedSuperhero.getFirstName());
        superheroFromDatabase.setLastName(userModifiedSuperhero.getLastName());
        superheroFromDatabase.setSuperheroName(userModifiedSuperhero.getSuperheroName());
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteSuperhero(@PathVariable final Long id) {
        superheroService.deleteSuperhero(id);
    }
}
