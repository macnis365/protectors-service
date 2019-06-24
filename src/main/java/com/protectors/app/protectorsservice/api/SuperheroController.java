package com.protectors.app.protectorsservice.api;

import com.protectors.app.protectorsservice.entity.Superhero;
import com.protectors.app.protectorsservice.service.SuperheroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(path = "/superhero")
public class SuperheroController {

    @Autowired
    private SuperheroService superheroService;

    @PostMapping
    public ResponseEntity createSuperhero(final Superhero superhero) {
        Long newSuperheroId = superheroService.createSuperhero(superhero);
        URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/{id}").buildAndExpand(newSuperheroId).toUri();
        return ResponseEntity.created(location).build();
    }
}
