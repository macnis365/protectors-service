package com.protectors.app.protectorsservice.utility;

import com.protectors.app.protectorsservice.entity.Mission;
import com.protectors.app.protectorsservice.entity.Superhero;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

public class CompareUtilityTest {

    @Test
    public void testAmendMatchedMission() {
        Set<Mission> userModifiedMissions = new HashSet<>();
        Mission mission1 = new Mission();
        mission1.setId(1L);
        mission1.setName("ms1");
        mission1.setCompleted(false);
        mission1.setDeleted(false);
        userModifiedMissions.add(mission1);

        Mission mission2 = new Mission();
        mission2.setId(2L);
        mission2.setName("ms2");
        mission2.setCompleted(false);
        mission2.setDeleted(false);
        userModifiedMissions.add(mission2);

        Mission mission3 = new Mission();
        mission3.setId(2L);
        mission3.setName("ms3");
        mission3.setCompleted(false);
        mission3.setDeleted(true);

        Mission amendMatchedMission = CompareUtility.amendMatchedMission(mission3, userModifiedMissions);
        Assert.assertEquals(mission2.getId(), amendMatchedMission.getId());
        Assert.assertEquals(mission2.isCompleted(), amendMatchedMission.isCompleted());
        Assert.assertEquals(mission2.isDeleted(), amendMatchedMission.isDeleted());
        Assert.assertEquals(mission2, amendMatchedMission);

    }

    @Test
    public void updateOldMissionTest() {
        Set<Mission> userModifiedMissions = new HashSet<>();
        Mission mission1 = new Mission();
        mission1.setId(1L);
        mission1.setName("ms1");
        mission1.setCompleted(false);
        mission1.setDeleted(false);
        userModifiedMissions.add(mission1);

        Mission mission2 = new Mission();
        mission2.setId(2L);
        mission2.setName("ms2");
        mission2.setCompleted(false);
        mission2.setDeleted(false);
        userModifiedMissions.add(mission2);

        Superhero superheroFromDataBase = new Superhero();
        superheroFromDataBase.setId(22L);
        superheroFromDataBase.setSuperheroName("new name");
        superheroFromDataBase.setFirstName("first");
        superheroFromDataBase.setLastName("last");
        superheroFromDataBase.setMissions(userModifiedMissions);

        Set<Mission> oldMissions = new HashSet<>();
        Mission mission3 = new Mission();
        mission3.setId(2L);
        mission3.setName("ms3");
        mission3.setCompleted(true);
        mission3.setDeleted(true);
        oldMissions.add(mission3);

        CompareUtility.findMissionsToUpdate(superheroFromDataBase, oldMissions);
    }

    @Test
    public void findMissionsToUpdateDatabase() {
        Set<Mission> userOldModifiedMissions = new HashSet<>();
        Mission mission1 = new Mission();
        mission1.setId(1L);
        mission1.setName("ms1");
        mission1.setCompleted(false);
        mission1.setDeleted(false);
        userOldModifiedMissions.add(mission1);

        Mission mission2 = new Mission();
        mission2.setId(2L);
        mission2.setName("ms2");
        mission2.setCompleted(false);
        mission2.setDeleted(false);
        userOldModifiedMissions.add(mission2);

        Superhero superheroFromDataBase = new Superhero();
        superheroFromDataBase.setId(22L);
        superheroFromDataBase.setSuperheroName("new name");
        superheroFromDataBase.setFirstName("first");
        superheroFromDataBase.setLastName("last");
        superheroFromDataBase.setMissions(userOldModifiedMissions);

        Set<Mission> missionsFromDatabase = new HashSet<>();
        Mission mission3 = new Mission();
        mission3.setId(2L);
        mission3.setName("ms3");
        mission3.setCompleted(true);
        mission3.setDeleted(true);
        missionsFromDatabase.add(mission3);

        Set<Mission> missionsToUpdate = CompareUtility.findMissionsToUpdate(superheroFromDataBase, userOldModifiedMissions);
        for (Mission mission : missionsToUpdate) {
            if (mission.getId().equals(mission2.getId())) {
                Assert.assertEquals(mission.getName(), mission2.getName());
            }
        }
    }

    @Test
    public void updatePersistedMissionsTest() {

        Superhero userModifiedSuperhero = new Superhero();
        Set<Mission> userModifiedMissions = new HashSet<>();
        Mission mission1 = new Mission();
        mission1.setId(1L);
        mission1.setName("ms1");
        mission1.setCompleted(false);
        mission1.setDeleted(false);
        userModifiedMissions.add(mission1);

        Mission mission2 = new Mission();
        mission2.setId(2L);
        mission2.setName("ms2");
        mission2.setCompleted(false);
        mission2.setDeleted(false);
        userModifiedMissions.add(mission2);
        userModifiedSuperhero.setMissions(userModifiedMissions);
        userModifiedSuperhero.setId(22L);
        userModifiedSuperhero.setSuperheroName("new name");
        userModifiedSuperhero.setFirstName("firstmod");
        userModifiedSuperhero.setLastName("lastmod");

        Superhero superheroFromDataBase = new Superhero();
        Set<Mission> oldMissions = new HashSet<>();
        Mission mission3 = new Mission();
        mission3.setId(2L);
        mission3.setName("ms3");
        mission3.setCompleted(false);
        mission3.setDeleted(true);
        oldMissions.add(mission3);

        Mission mission4 = new Mission();
        mission4.setId(1L);
        mission4.setName("4");
        mission4.setCompleted(true);
        mission4.setDeleted(false);
        oldMissions.add(mission4);

        superheroFromDataBase.setId(22L);
        superheroFromDataBase.setSuperheroName("new name");
        superheroFromDataBase.setFirstName("first");
        superheroFromDataBase.setLastName("last");
        superheroFromDataBase.setMissions(oldMissions);

        CompareUtility.updatePersistedMissions(userModifiedSuperhero, superheroFromDataBase);
    }
}
