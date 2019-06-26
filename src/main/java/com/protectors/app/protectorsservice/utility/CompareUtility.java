package com.protectors.app.protectorsservice.utility;

import com.protectors.app.protectorsservice.customexception.CompletedMissionCannotDelete;
import com.protectors.app.protectorsservice.entity.Mission;
import com.protectors.app.protectorsservice.entity.Superhero;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
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
            System.out.println("inside amend matched" + missionToUpdate);
        }
        return missionToUpdate;
    }

    public static void updatePersistedMissions(Superhero userModifiedSuperhero, Superhero superheroFromDatabase) {
        if (!CollectionUtils.isEmpty(userModifiedSuperhero.getMissions())) {
            Set<Mission> oldMissionsInUserModified = CompareUtility.getMatchedMissionsFrom(userModifiedSuperhero.getMissions(), superheroFromDatabase.getMissions());
            Set<Mission> unMatchedMissions = CompareUtility.getUnMatchedMissions(userModifiedSuperhero.getMissions(), superheroFromDatabase.getMissions());
            superheroFromDatabase.getMissions().addAll(unMatchedMissions);
            superheroFromDatabase.getMissions().removeAll(oldMissionsInUserModified);
            oldMissionsInUserModified.stream().forEach(System.out::println);
            Set<Mission> updatedMissions = findMissionsToUpdate(superheroFromDatabase, oldMissionsInUserModified);
            superheroFromDatabase.getMissions().addAll(updatedMissions);
        }
    }

    public static Set<Mission> findMissionsToUpdate(Superhero superheroFromDatabase, Set<Mission> oldMissionsInUserModified) {
        Set<Mission> updatedMissions = new HashSet<>();
        for (Mission mission : superheroFromDatabase.getMissions()) {
            Mission updateToDatabase = CompareUtility.amendMatchedMission(mission, oldMissionsInUserModified);
            updatedMissions.add(updateToDatabase);
        }
        return updatedMissions;
    }
}