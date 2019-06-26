package com.protectors.app.protectorsservice.utility;

import com.protectors.app.protectorsservice.customexception.CompletedMissionCannotDelete;
import com.protectors.app.protectorsservice.entity.Mission;

import java.util.Set;
import java.util.stream.Collectors;

public class CompareUtility {
    public static Set<Mission> getMatchedMissionsFrom(Set<Mission> firstMissionSet, Set<Mission> secondMissionSet) {
        return firstMissionSet.stream().filter(firstSetMission -> secondMissionSet.contains(firstSetMission)).collect(Collectors.toSet());
    }

    public static Set<Mission> getUnMatchedMissions(Set<Mission> firstMissionSet, Set<Mission> secondMissionSet) {
        return firstMissionSet.stream().filter(firstSetMission -> !secondMissionSet.contains(firstSetMission)).collect(Collectors.toSet());
    }

    public static Mission amendMatchedMission(Mission missionToUpdate, Set<Mission> missionSet) {
        for (Mission mission : missionSet) {
            if (missionToUpdate.equals(mission)) {
                if (Boolean.TRUE.equals(mission.isCompleted()) && Boolean.TRUE.equals(mission.isDeleted())) {
                    throw new CompletedMissionCannotDelete(mission.getId());
                }
                missionToUpdate.setName(mission.getName());
                missionToUpdate.setDeleted(mission.isDeleted());
                missionToUpdate.setCompleted(mission.isCompleted());
            }
        }
        return missionToUpdate;
    }
}
