package com.protectors.app.protectorsservice.service;

import com.protectors.app.protectorsservice.customexception.ActiveMissionCannotDelete;
import com.protectors.app.protectorsservice.customexception.SuperheroNotFound;
import com.protectors.app.protectorsservice.entity.Mission;
import com.protectors.app.protectorsservice.entity.Superhero;
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
public class SuperheroServiceIntegrationTest {

    @TestConfiguration
    static class SuperheroServiceTestContextConfiguration {

        @Bean
        public SuperheroService employeeService() {
            return new SuperheroService();
        }
    }

    @Autowired
    private SuperheroService superheroService;

    @MockBean
    private SuperheroRepository superheroRepository;

    Superhero superhero = new Superhero();

    @Before
    public void setUp() {
        superhero.setId(121L);
        superhero.setSuperheroName("Superman");
        superhero.setFirstName("Clark");
        superhero.setLastName("Kent");
        Mission mission = new Mission();
        mission.setName("superhero mission");
        mission.setCompleted(true);
        mission.setDeleted(false);
        superhero.getMissions().add(mission);

        Mockito.when(superheroRepository.save(superhero))
                .thenReturn(superhero);
        Mockito.when(superheroRepository.findById(121L)).thenReturn(Optional.ofNullable(superhero));

    }

    @Test
    public void saveSuperheroNameAndReturn() {
        Superhero createdHero = superheroService.createSuperhero(superhero);

        assertThat(createdHero.getFirstName()).isEqualTo(superhero.getFirstName());
        assertThat(createdHero.getLastName()).isEqualTo(superhero.getLastName());
        assertThat(createdHero.getSuperheroName()).isEqualTo(superhero.getSuperheroName());
        assertThat(createdHero.getMissions().size()).isEqualTo(superhero.getMissions().size());
    }

    @Test(expected = ActiveMissionCannotDelete.class)
    public void saveSuperheroName() {
        Mission mission = new Mission();
        mission.setId(1L);
        mission.setName("completed mission");
        mission.setCompleted(false);
        mission.setDeleted(true);
        superhero.getMissions().add(mission);
        superheroService.createSuperhero(superhero);
    }

    @Test(expected = SuperheroNotFound.class)
    public void getSuperheroWhichNotExist() {
        superheroService.findSuperhero(12L);
    }

    @Test
    public void getSuperheroWhichExist() {
        Superhero superheroFound = superheroService.findSuperhero(121L);
        Assert.assertEquals(superhero.getId(), superheroFound.getId());
    }

    @Test
    public void updateWithUserModifiedSuperhero() {
        Superhero userModifiedSuperhero = new Superhero();
        userModifiedSuperhero.setId(121L);
        userModifiedSuperhero.setFirstName("Tony");
        userModifiedSuperhero.setFirstName("Stark");
        userModifiedSuperhero.setSuperheroName("Iron Man");
        Mission mission = new Mission();
        mission.setName("Endgame");
        mission.setDeleted(false);
        mission.setCompleted(true);
        userModifiedSuperhero.getMissions().add(mission);
        Mockito.when(superheroRepository.save(userModifiedSuperhero)).thenReturn(userModifiedSuperhero);
        Superhero superheroUpdated = superheroService.updateSuperhero(121L, userModifiedSuperhero);
        Assert.assertEquals(userModifiedSuperhero.getId(), superheroUpdated.getId());
        Assert.assertEquals(userModifiedSuperhero.getFirstName(), superheroUpdated.getFirstName());
        Assert.assertEquals(userModifiedSuperhero.getLastName(), superheroUpdated.getLastName());
        Assert.assertEquals(userModifiedSuperhero.getMissions().size(), superheroUpdated.getMissions().size());
    }

    @Test(expected = SuperheroNotFound.class)
    public void updateWithUserModifiedNotExistSuperhero() {
        Superhero superheroUpdated = superheroService.updateSuperhero(11L, superhero);
    }

    @Test
    public void deleteExistSuperhero() {
        superheroService.deleteSuperhero(121L);
    }

    @Test(expected = SuperheroNotFound.class)
    public void deleteNonExistSuperhero() {
        superheroService.deleteSuperhero(11L);
    }

}
