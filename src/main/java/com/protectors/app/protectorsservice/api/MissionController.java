package com.protectors.app.protectorsservice.api;

import com.protectors.app.protectorsservice.customexception.SuperheroNotFound;
import com.protectors.app.protectorsservice.entity.Mission;
import com.protectors.app.protectorsservice.service.MissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/mission")
public class MissionController {

    @Autowired
    private MissionService missionService;

    @GetMapping("{id}")
    public Mission fetchSuperHero(@PathVariable final Long id) throws SuperheroNotFound {
        return missionService.findMission(id).orElseThrow(() -> new SuperheroNotFound());
    }
}
