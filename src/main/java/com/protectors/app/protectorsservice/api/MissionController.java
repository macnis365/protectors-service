package com.protectors.app.protectorsservice.api;

import com.protectors.app.protectorsservice.customexception.MissionNotFound;
import com.protectors.app.protectorsservice.entity.Mission;
import com.protectors.app.protectorsservice.service.MissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/mission")
public class MissionController {

    @Autowired
    private MissionService missionService;

    @GetMapping("{id}")
    public Mission fetchSuperHero(@PathVariable final Long id) {
        return missionService.findMission(id).orElseThrow(() -> new MissionNotFound(id));
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Mission createMission(@RequestBody Mission mission) {
        return missionService.saveOrUpdate(mission);
    }

    @PutMapping("{id}")
    public Mission amendMission(@RequestBody Mission mission, @PathVariable final Long id) {
        return missionService.update(id, mission);
    }

    @DeleteMapping("{id}")
    public Mission softDeleteMission(@PathVariable final Long id) {
        return missionService.softDeleteMission(id);
    }
}
