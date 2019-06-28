package com.protectors.app.protectorsservice.utility;

import com.protectors.app.protectorsservice.entity.Mission;
import com.protectors.app.protectorsservice.entity.Superhero;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MissionUtilityTest {

    @Test
    public void testAmendMatchedMission() {
        Mission mission1 = new Mission.MissionBuilder().setId(1L).setName("ms1").setCompleted(false).setDeleted(false).build();
        Mission mission2 = new Mission.MissionBuilder().setId(2L).setName("ms2").setCompleted(false).setDeleted(false).build();
        Set<Mission> userModifiedMissions = Stream.of(mission1, mission2).collect(Collectors.toSet());
        Mission mission3 = new Mission.MissionBuilder().setId(2L).setName("ms3").setCompleted(false).setDeleted(true).build();
        Mission amendMatchedMission = MissionUtility.amendMatchedMission(mission3, userModifiedMissions);
        Assert.assertEquals(mission2.getId(), amendMatchedMission.getId());
        Assert.assertEquals(mission2.isCompleted(), amendMatchedMission.isCompleted());
        Assert.assertEquals(mission2.isDeleted(), amendMatchedMission.isDeleted());
        Assert.assertEquals(mission2, amendMatchedMission);

    }

    @Test
    public void updateOldMissionTest() {
        Mission mission1 = new Mission.MissionBuilder().setId(1L).setName("ms1").setCompleted(false).setDeleted(false).build();
        Mission mission2 = new Mission.MissionBuilder().setId(2L).setName("ms2").setCompleted(false).setDeleted(false).build();
        Set<Mission> userModifiedMissions = Stream.of(mission1, mission2).collect(Collectors.toSet());
        Superhero superheroFromDataBase = new Superhero.SuperheroBuilder().setId(22L).setSuperheroName("new name").setFirstName("first").setLastName("last").setMissions(userModifiedMissions).build();
        Mission mission3 = new Mission.MissionBuilder().setId(2L).setName("ms3").setCompleted(true).setDeleted(true).build();
        Set<Mission> oldMissions = Stream.of(mission3).collect(Collectors.toSet());
        MissionUtility.findMissionsToUpdate(superheroFromDataBase, oldMissions);
    }

    @Test
    public void findMissionsToUpdateDatabase() {
        Mission mission1 = new Mission.MissionBuilder().setId(1L).setName("ms1").setCompleted(false).setDeleted(false).build();
        Mission mission2 = new Mission.MissionBuilder().setId(2L).setName("ms2").setCompleted(false).setDeleted(false).build();
        Mission mission3 = new Mission.MissionBuilder().setId(2L).setName("ms3").setCompleted(true).setDeleted(true).build();
        Set<Mission> userOldModifiedMissions = Stream.of(mission1, mission2).collect(Collectors.toSet());
        Set<Mission> missionsFromDatabase = Stream.of(mission3).collect(Collectors.toSet());
        Superhero superheroFromDataBase = new Superhero.SuperheroBuilder().setId(22L).setSuperheroName("new name").setFirstName("first").setLastName("last").setMissions(missionsFromDatabase).build();
        Set<Mission> missionsToUpdate = MissionUtility.findMissionsToUpdate(superheroFromDataBase, userOldModifiedMissions);
        for (Mission mission : missionsToUpdate) {
            if (mission.getId().equals(mission2.getId())) {
                Assert.assertEquals(mission.getName(), mission2.getName());
            }
        }
    }

    @Test
    public void updatePersistedMissionsTest() {
        Mission mission1 = new Mission.MissionBuilder().setId(1L).setName("ms1").setCompleted(false).setDeleted(false).build();
        Mission mission2 = new Mission.MissionBuilder().setId(2L).setName("ms2").setCompleted(false).setDeleted(false).build();
        Mission mission3 = new Mission.MissionBuilder().setId(2L).setName("ms3").setCompleted(true).setDeleted(true).build();
        Mission mission4 = new Mission.MissionBuilder().setId(1L).setName("4").setCompleted(true).setDeleted(false).build();
        Set<Mission> userModifiedMissions = Stream.of(mission1, mission2).collect(Collectors.toSet());
        Set<Mission> oldMissions = Stream.of(mission3).collect(Collectors.toSet());
        Superhero userModifiedSuperhero = new Superhero.SuperheroBuilder().setId(22L).setSuperheroName("new name").setFirstName("firstmod").setLastName("lastmod").setMissions(userModifiedMissions).build();
        Superhero superheroFromDataBase = new Superhero.SuperheroBuilder().setId(22L).setSuperheroName("new name").setFirstName("firstmod").setLastName("lastmod").setMissions(userModifiedMissions).build();
        MissionUtility.updatePersistedMissions(userModifiedSuperhero, superheroFromDataBase);
    }
}
