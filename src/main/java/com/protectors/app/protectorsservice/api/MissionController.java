package com.protectors.app.protectorsservice.api;

import com.protectors.app.protectorsservice.entity.Mission;
import com.protectors.app.protectorsservice.service.MissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/mission")
public class MissionController {

    @Autowired
    private MissionService missionService;

    @GetMapping("{id}")
    public Mission fetchSuperHero(@PathVariable final Long id) {
        return missionService.findMission(id);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Mission createMission(@Valid @RequestBody Mission mission) {
        return missionService.createMission(mission);
    }

    @PutMapping("{id}")
    public Mission amendMission(@Valid @RequestBody Mission mission, @PathVariable final Long id) {
        return missionService.update(id, mission);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void softDeleteMission(@PathVariable final Long id) {
        missionService.softDeleteMission(id);
    }
}
