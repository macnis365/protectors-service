package com.protectors.app.protectorsservice.api;

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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Superhero createSuperhero(final Superhero superhero) {
        return superheroService.createSuperhero(superhero);
    }
}
