package com.protectors.app.protectorsservice.utility;

import com.protectors.app.protectorsservice.customexception.ActiveMissionCannotDelete;
import com.protectors.app.protectorsservice.entity.Mission;
import com.protectors.app.protectorsservice.entity.Superhero;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class MissionUtility {
    public static Set<Mission> getMatchedMissionsFrom(Set<Mission> firstMissionSet, Set<Mission> secondMissionSet) {
        return firstMissionSet.stream().filter(firstSetMission -> secondMissionSet.contains(firstSetMission)).collect(Collectors.toSet());
    }

    public static Set<Mission> getUnMatchedMissions(Set<Mission> firstMissionSet, Set<Mission> secondMissionSet) {
        return firstMissionSet.stream().filter(firstSetMission -> !secondMissionSet.contains(firstSetMission)).collect(Collectors.toSet());
    }

    public static Mission amendMatchedMission(Mission missionToUpdate, Set<Mission> missionSet) {
        for (Mission mission : missionSet) {
            if (missionToUpdate.equals(mission)) {
                validateActiveMission(mission);
                missionToUpdate.setName(mission.getName());
                missionToUpdate.setCompleted(mission.isCompleted());
            }
        }
        return missionToUpdate;
    }

    public static void validateActiveMission(Mission mission) {
        if (Boolean.FALSE.equals(mission.isCompleted()) && Boolean.TRUE.equals(mission.isDeleted())) {
            throw new ActiveMissionCannotDelete(mission.getId());
        }
    }

    public static void validateActiveMissions(Set<Mission> missions) {
        for (Mission mission : missions) {
            validateActiveMission(mission);
        }
    }

    public static void updatePersistedMissions(Superhero userModifiedSuperhero, Superhero superheroFromDatabase) {
        if (!CollectionUtils.isEmpty(userModifiedSuperhero.getMissions())) {
            Set<Mission> oldMissionsUserModified = MissionUtility.getMatchedMissionsFrom(userModifiedSuperhero.getMissions(), superheroFromDatabase.getMissions());
            Set<Mission> newMissions = MissionUtility.getUnMatchedMissions(userModifiedSuperhero.getMissions(), superheroFromDatabase.getMissions());
            superheroFromDatabase.getMissions().addAll(newMissions);
            Set<Mission> updatedMissions = findMissionsToUpdate(superheroFromDatabase, oldMissionsUserModified);
            Set<Mission> removeAssociatedMissions = MissionUtility.getUnMatchedMissions(superheroFromDatabase.getMissions(),userModifiedSuperhero.getMissions());
            superheroFromDatabase.getMissions().removeAll(removeAssociatedMissions);
            superheroFromDatabase.getMissions().removeAll(oldMissionsUserModified);
            superheroFromDatabase.getMissions().addAll(updatedMissions);
        }
    }

    public static Set<Mission> findMissionsToUpdate(Superhero superheroFromDatabase, Set<Mission> oldMissionsUserModified) {
        Set<Mission> updatedMissions = new HashSet<>();
        for (Mission mission : superheroFromDatabase.getMissions()) {
            Mission updateToDatabase = MissionUtility.amendMatchedMission(mission, oldMissionsUserModified);
            updatedMissions.add(updateToDatabase);
        }
        return updatedMissions;
    }
}
