package com.protectors.app.protectorsservice;

import com.protectors.app.protectorsservice.entity.Superhero;
import com.protectors.app.protectorsservice.repository.SuperheroRepository;
import com.protectors.app.protectorsservice.service.SuperheroService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

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
        superhero.setSuperheroName("Superman");
        superhero.setFirstName("Clark");
        superhero.setLastName("Kent");

        Mockito.when(superheroRepository.save(superhero))
                .thenReturn(superhero);
    }

    @Test
    public void whenValidName_thenEmployeeShouldBeFound() {
        String name = "alex";
        Superhero found = superheroService.saveOrUpdateSuperhero(superhero);

        assertThat(found.getFirstName())
                .isEqualTo(superhero.getFirstName());
    }

}
