package com.protectors.app.protectorsservice.service;

import com.protectors.app.protectorsservice.customexception.ActiveMissionCannotDelete;
import com.protectors.app.protectorsservice.customexception.MissionNotFound;
import com.protectors.app.protectorsservice.entity.Mission;
import com.protectors.app.protectorsservice.repository.MissionRepository;
import com.protectors.app.protectorsservice.utility.MissionUtility;
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
        MissionUtility.validateActiveMission(mission);
        return missionRepository.save(mission);
    }

    public Mission update(Long id, Mission mission) {
        Mission missionFromDatabase = missionRepository.findById(id).orElseThrow(() -> new MissionNotFound(id));
        if (Boolean.TRUE.equals(mission.isCompleted() && Boolean.TRUE.equals(mission.isDeleted()))) {
            throw new ActiveMissionCannotDelete(id);
        }

        missionFromDatabase.setName(mission.getName());
        missionFromDatabase.setDeleted(mission.isDeleted());
        missionFromDatabase.setCompleted(mission.isCompleted());
        return missionRepository.save(missionFromDatabase);
    }


    public Mission softDeleteMission(Long id) {
        Mission mission = missionRepository.findById(id).orElseThrow(() -> new MissionNotFound(id));
        MissionUtility.validateActiveMission(mission);
        mission.setDeleted(Boolean.TRUE);
        return missionRepository.save(mission);
    }
}
