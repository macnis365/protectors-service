package com.protectors.app.protectorsservice.api;

import com.protectors.app.protectorsservice.entity.Superhero;
import com.protectors.app.protectorsservice.service.SuperheroService;
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
        return superheroService.createSuperhero(superhero);
    }

    @GetMapping("{id}")
    public Superhero fetchSuperhero(@PathVariable final Long id) {
        return superheroService.findSuperhero(id);
    }

    @PutMapping("{id}")
    public Superhero amendSuperhero(@Valid @RequestBody Superhero userModifiedSuperhero, @PathVariable final Long id) {
        return superheroService.updateSuperhero(id, userModifiedSuperhero);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteSuperhero(@PathVariable final Long id) {
        superheroService.deleteSuperhero(id);
    }
}
