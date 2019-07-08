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
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/*
@RunWith(SpringRunner.class)
*/
public class SuperheroServiceIntegrationTest {
/*
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

    Superhero superhero;

    @Before
    public void setUp() {
        Mission mission = new Mission.MissionBuilder().setId(2L).setName("superhero mission").setCompleted(true).setDeleted(false).build();
        superhero = new Superhero.SuperheroBuilder().setId(121L).setSuperheroName("Superman").setFirstName("Clark").setLastName("Kent").setMissions(Stream.of(mission).collect(Collectors.toSet())).build();
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
        Mission mission = new Mission.MissionBuilder().setId(2L).setName("superhero mission").setCompleted(false).setDeleted(true).build();
        Superhero hero = new Superhero.SuperheroBuilder().setId(121L).setSuperheroName("Superman").setFirstName("Clark").setLastName("Kent").setMissions(Stream.of(mission).collect(Collectors.toSet())).build();
        superheroService.createSuperhero(hero);
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
        Mission mission = new Mission.MissionBuilder().setId(2L).setName("Endgame").setCompleted(true).setDeleted(true).build();
        Superhero userModifiedSuperhero = new Superhero.SuperheroBuilder().setId(121L).setSuperheroName("Iron Man").setFirstName("Tony").setLastName("Stark").setMissions(Stream.of(mission).collect(Collectors.toSet())).build();
        Mockito.when(superheroRepository.save(userModifiedSuperhero)).thenReturn(userModifiedSuperhero);
        Superhero superheroUpdated = superheroService.updateSuperhero(121L, userModifiedSuperhero);
        Assert.assertEquals(userModifiedSuperhero.getId(), superheroUpdated.getId());
        Assert.assertEquals(userModifiedSuperhero.getFirstName(), superheroUpdated.getFirstName());
        Assert.assertEquals(userModifiedSuperhero.getLastName(), superheroUpdated.getLastName());
        Assert.assertEquals(userModifiedSuperhero.getMissions().size(), superheroUpdated.getMissions().size());
    }

    @Test(expected = SuperheroNotFound.class)
    public void updateWithUserModifiedNotExistSuperhero() {
        superheroService.updateSuperhero(11L, superhero);
    }

    @Test
    public void deleteExistSuperhero() {
        superheroService.deleteSuperhero(121L);
    }

    @Test(expected = SuperheroNotFound.class)
    public void deleteNonExistSuperhero() {
        superheroService.deleteSuperhero(11L);
    }*/

}