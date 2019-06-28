package com.protectors.app.protectorsservice.service;

import com.protectors.app.protectorsservice.entity.Mission;
import com.protectors.app.protectorsservice.repository.MissionRepository;
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
        mission.setDeleted(true);

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

}
