package com.protectors.app.protectorsservice.api;

import com.protectors.app.protectorsservice.customexception.InvalidInputException;
import com.protectors.app.protectorsservice.customexception.InvalidOperation;
import com.protectors.app.protectorsservice.customexception.SuperheroNotFound;
import com.protectors.app.protectorsservice.entity.Mission;
import com.protectors.app.protectorsservice.entity.Superhero;
import com.protectors.app.protectorsservice.service.SuperheroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

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
    public Superhero fetchSuperHero(@PathVariable final Long id) {
        return superheroService.findSuperhero(id).orElseThrow(() -> new SuperheroNotFound(id));
    }

    @PutMapping("{id}")
    public Superhero amendSuperHero(@RequestBody Superhero superhero, @PathVariable final Long id) throws InvalidOperation {
        if (null == superhero) {
            throw new InvalidInputException();
        }
        System.out.println(superhero.toString());

        return superheroService.findSuperhero(id).map((Superhero newSuperhero) -> {

                    if (!StringUtils.isEmpty(superhero.getFirstName())) {
                        newSuperhero.setFirstName(superhero.getFirstName());
                    }

                    if (!StringUtils.isEmpty(superhero.getLastName())) {
                        newSuperhero.setLastName(superhero.getLastName());
                    }

                    if (!StringUtils.isEmpty(superhero.getSuperheroName())) {
                        newSuperhero.setSuperheroName(superhero.getSuperheroName());
                    }

                    Set<String> missionNamesInDatabase = new HashSet<>();
                    for (Mission mission : newSuperhero.getMissions()) {
                        missionNamesInDatabase.add(mission.getName());
                    }

                    if (!CollectionUtils.isEmpty(superhero.getMissions())) {
                        for (Mission mission : superhero.getMissions()) {
                            if (missionNamesInDatabase.contains(mission.getName())) {
                                System.out.println(mission.toString());
                            }
                        }
                    }


/*                    if (!CollectionUtils.isEmpty(superhero.getMissions())) {
                        if (!CollectionUtils.isEmpty(newSuperhero.getMissions())) {
                            for (Mission mission : newSuperhero.getMissions()) {
                                if (superhero.getMissions().contains(mission)) {
//                              TO DO update missions details
                                } else {
//                              TO DO add new missions to superhero
                                }
                            }
                        } else {
//                            TO DO  add new missions to superhero

                        }
                    }*/

                    return superheroService.saveOrUpdateSuperhero(newSuperhero);
                }
        ).

                orElseThrow(() -> new

                        InvalidOperation());
    }
}
