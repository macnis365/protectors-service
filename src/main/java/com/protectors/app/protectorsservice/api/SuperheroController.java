package com.protectors.app.protectorsservice.api;

import com.protectors.app.protectorsservice.customexception.SuperheroNotFound;
import com.protectors.app.protectorsservice.entity.Mission;
import com.protectors.app.protectorsservice.entity.Superhero;
import com.protectors.app.protectorsservice.service.SuperheroService;
import com.protectors.app.protectorsservice.utility.CompareUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

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
    public Superhero fetchSuperHero(@PathVariable final Long id) {
        return superheroService.findSuperhero(id).orElseThrow(() -> new SuperheroNotFound(id));
    }

    @PutMapping("{id}")
    public Superhero amendSuperHero(@Valid @RequestBody Superhero modifiedSuperhero, @PathVariable final Long id) {
        return superheroService.findSuperhero(id).map((Superhero superheroFromDatabase) -> {
                    superheroFromDatabase.setFirstName(modifiedSuperhero.getFirstName());
                    superheroFromDatabase.setLastName(modifiedSuperhero.getLastName());
                    superheroFromDatabase.setSuperheroName(modifiedSuperhero.getSuperheroName());
                    if (!CollectionUtils.isEmpty(modifiedSuperhero.getMissions())) {
                        Set<Mission> newMissions = CompareUtility.getMatchedMissionsFrom(modifiedSuperhero.getMissions(), superheroFromDatabase.getMissions());
                        newMissions.stream().forEach(System.out::println);
                        System.out.println("Matched missions above");
                        Set<Mission> unMatchedMissions = CompareUtility.getUnMatchedMissions(modifiedSuperhero.getMissions(), superheroFromDatabase.getMissions());
                        unMatchedMissions.stream().forEach(System.out::println);
                        System.out.println("UnMatched missions above");
                        superheroFromDatabase.getMissions().addAll(newMissions);
                        for (Mission mission : superheroFromDatabase.getMissions()) {
                            superheroFromDatabase.getMissions().add(CompareUtility.amendMatchedMission(mission, modifiedSuperhero.getMissions()));
                        }
                    }
                    return superheroService.saveOrUpdateSuperhero(superheroFromDatabase);
                }
        ).orElseThrow(() -> new SuperheroNotFound(id));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteSuperhero(@PathVariable final Long id) {
        superheroService.deleteSuperhero(id);
    }
}
