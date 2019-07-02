package com.protectors.app.protectorsservice.service;

import com.protectors.app.protectorsservice.customexception.ActiveMissionCannotDelete;
import com.protectors.app.protectorsservice.customexception.MissionNotFound;
import com.protectors.app.protectorsservice.entity.Mission;
import com.protectors.app.protectorsservice.repository.MissionRepository;
import com.protectors.app.protectorsservice.repository.SuperheroRepository;
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
    
    @MockBean
    private SuperheroRepository superheroRepository;

    Mission mission;

    @Before
    public void setUp() {
        mission = new Mission.MissionBuilder().setId(2L).setName("infinity war").setCompleted(true).setDeleted(false).build();
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
        missionService.createMission(this.mission);
    }

    @Test
    public void getExistingMission() {
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
        Mission userModifiedMission = new Mission.MissionBuilder().setId(2L).setName("far from home").setCompleted(false).setDeleted(false).build();
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
        Mission softDeleteMission = new Mission.MissionBuilder().setId(2L).setName("infinity war").setCompleted(true).setDeleted(true).build();
        Mockito.when(missionRepository.save(mission))
                .thenReturn(softDeleteMission);
        missionService.softDeleteMission(2L);
    }

    @Test(expected = MissionNotFound.class)
    public void deleteNonExistMission() {
        missionService.softDeleteMission(3L);
    }

}
