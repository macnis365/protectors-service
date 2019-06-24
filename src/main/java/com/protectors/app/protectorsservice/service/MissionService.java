package com.protectors.app.protectorsservice.service;

import com.protectors.app.protectorsservice.entity.Mission;
import com.protectors.app.protectorsservice.repository.MissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MissionService {
    @Autowired
    private MissionRepository missionRepository;

    public Optional<Mission> findMission(Long id){
        return missionRepository.findById(id);
    }

    public Mission saveOrUpdate(Mission mission){
        return missionRepository.save(mission);
    }
}
