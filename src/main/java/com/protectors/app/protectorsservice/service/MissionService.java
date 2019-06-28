package com.protectors.app.protectorsservice.service;

import com.protectors.app.protectorsservice.customexception.CompletedMissionCannotDelete;
import com.protectors.app.protectorsservice.customexception.MissionNotFound;
import com.protectors.app.protectorsservice.entity.Mission;
import com.protectors.app.protectorsservice.repository.MissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MissionService {
    @Autowired
    private MissionRepository missionRepository;

    public Mission findMission(Long id) {
        return missionRepository.findById(id).orElseThrow(() -> new MissionNotFound(id));
    }

    public Mission createMission(Mission mission) {
        return missionRepository.save(mission);
    }

    public Mission update(Long id, Mission mission) {
        Mission missionFromDatabase = missionRepository.findById(id).orElseThrow(() -> new MissionNotFound(id));
        if (Boolean.TRUE.equals(mission.isCompleted() && Boolean.TRUE.equals(mission.isDeleted()))) {
            throw new CompletedMissionCannotDelete(id);
        }

        missionFromDatabase.setName(mission.getName());
        missionFromDatabase.setDeleted(mission.isDeleted());
        missionFromDatabase.setCompleted(mission.isCompleted());
        return missionRepository.save(missionFromDatabase);
    }


    public Mission softDeleteMission(Long id) {
        Mission mission = missionRepository.findById(id).orElseThrow(() -> new MissionNotFound(id));
        if (Boolean.TRUE.equals(mission.isCompleted())) {
            throw new CompletedMissionCannotDelete(id);
        }
        mission.setDeleted(Boolean.TRUE);
        return missionRepository.save(mission);
    }
}
