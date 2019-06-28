package com.protectors.app.protectorsservice.service;

import com.protectors.app.protectorsservice.customexception.ActiveMissionCannotDelete;
import com.protectors.app.protectorsservice.customexception.MissionNotFound;
import com.protectors.app.protectorsservice.entity.Mission;
import com.protectors.app.protectorsservice.repository.MissionRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class MissionServiceIntegrationTest {

    @TestConfiguration
    static class MissionServiceIntegrationTestContextConfiguration {

        @Bean
        public MissionService missionService() {
            return new MissionService();
        }
    }

    @Autowired
    private MissionService missionService;

    @MockBean
    private MissionRepository missionRepository;

    Mission mission = new Mission();

    @Before
    public void setUp() {
        mission.setId(2L);
        mission.setName("infinity war");
        mission.setCompleted(true);
        mission.setDeleted(false);

        Mockito.when(missionRepository.save(mission))
                .thenReturn(mission);
        Mockito.when(missionRepository.findById(2L)).thenReturn(Optional.ofNullable(mission));

    }

    @Test
    public void saveMissionAndReturnSame() {
        Mission savedMission = missionService.createMission(this.mission);

        assertThat(mission.getId()).isEqualTo(savedMission.getId());
        assertThat(mission.getName()).isEqualTo(savedMission.getName());
        assertThat(mission.isDeleted()).isEqualTo(savedMission.isDeleted());
        assertThat(mission.isCompleted()).isEqualTo(savedMission.isCompleted());
    }

    @Test(expected = ActiveMissionCannotDelete.class)
    public void cannotSaveAndDeleteActiveMission() {
        mission.setCompleted(false);
        mission.setDeleted(true);
        Mission savedMission = missionService.createMission(this.mission);
    }

    @Test
    public void getExistingMission(){
        Mission missionFromDatabase = missionService.findMission(2L);
        Assert.assertEquals(mission.getId(), missionFromDatabase.getId());
        Assert.assertEquals(mission.getName(), missionFromDatabase.getName());
        Assert.assertEquals(mission.isCompleted(), missionFromDatabase.isCompleted());
        Assert.assertEquals(mission.isDeleted(), missionFromDatabase.isDeleted());
    }

    @Test(expected = MissionNotFound.class)
    public void getNonExistingMission() {
        missionService.findMission(1L);
    }

    @Test(expected = MissionNotFound.class)
    public void updateNonExistMissionWithUser() {
        missionService.update(1L, mission);
    }

    @Test
    public void updateExistingMissionWithUser() {
        Mission userModifiedMission = new Mission();
        userModifiedMission.setId(2L);
        userModifiedMission.setName("far from home");
        userModifiedMission.setCompleted(false);
        userModifiedMission.setDeleted(false);
        Mockito.when(missionRepository.save(mission))
                .thenReturn(userModifiedMission);
        Mission modifiedMission = missionService.update(2L, mission);
        Assert.assertEquals(userModifiedMission.getId(), modifiedMission.getId());
        Assert.assertEquals(userModifiedMission.getName(), modifiedMission.getName());
        Assert.assertEquals(userModifiedMission.isCompleted(), modifiedMission.isCompleted());
        Assert.assertEquals(userModifiedMission.isDeleted(), modifiedMission.isDeleted());
    }

    @Test
    public void deleteExistMission() {
        Mission softDeleteMission = new Mission();
        softDeleteMission.setDeleted(true);
        softDeleteMission.setCompleted(true);
        softDeleteMission.setName("infinity war");
        softDeleteMission.setId(2L);
        Mockito.when(missionRepository.save(mission))
                .thenReturn(softDeleteMission);
        Mission deletedMission = missionService.softDeleteMission(2L);
        Assert.assertEquals(softDeleteMission.getId(), deletedMission.getId());
        Assert.assertEquals(softDeleteMission.getName(), deletedMission.getName());
        Assert.assertEquals(softDeleteMission.isDeleted(), deletedMission.isDeleted());
        Assert.assertEquals(softDeleteMission.isCompleted(), deletedMission.isCompleted());
    }

    @Test(expected = MissionNotFound.class)
    public void deleteNonExistMission() {
        missionService.softDeleteMission(3L);
    }

}
