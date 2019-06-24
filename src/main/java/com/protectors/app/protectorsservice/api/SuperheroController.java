package com.protectors.app.protectorsservice.api;

import com.protectors.app.protectorsservice.customexception.InvalidOperation;
import com.protectors.app.protectorsservice.customexception.SuperheroNotFound;
import com.protectors.app.protectorsservice.entity.Superhero;
import com.protectors.app.protectorsservice.service.SuperheroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/superhero")
public class SuperheroController {

    @Autowired
    private SuperheroService superheroService;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Superhero createSuperhero(@RequestBody Superhero superhero) {
        return superheroService.saveOrUpdateSuperhero(superhero);
}

    @GetMapping("{id}")
    public Superhero fetchSuperHero(@PathVariable final Long id) throws SuperheroNotFound {
        return superheroService.findSuperhero(id).orElseThrow(() -> new SuperheroNotFound());
    }

    @PutMapping("{id}")
    public Superhero amendSuperHero(@RequestBody Superhero superhero, @PathVariable final Long id) throws InvalidOperation {
        return superheroService.findSuperhero(id).map((Superhero newSuperhero) -> {
                    newSuperhero.setFirstName(superhero.getFirstName());
                    newSuperhero.setLastName(superhero.getLastName());
                    newSuperhero.setSuperheroName(superhero.getSuperheroName());
                    return superheroService.saveOrUpdateSuperhero(newSuperhero);
                }
        ).orElseThrow(() -> new InvalidOperation());
    }
}
