package com.protectors.app.protectorsservice.repository;

import com.protectors.app.protectorsservice.entity.Superhero;
import com.protectors.app.protectorsservice.repository.SuperheroRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class SuperheroRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private SuperheroRepository superheroRepository;

    Superhero superhero = new Superhero();


    @Before
    public void setUp() {
        superhero.setSuperheroName("Superman");
        superhero.setFirstName("Clark");
        superhero.setLastName("Kent");
        entityManager.persist(superhero);
        entityManager.flush();
    }

    @Test
    public void createSuperheroAndReturn() {
        Superhero found = superheroRepository.save(superhero);
        assertThat(found.getSuperheroName())
                .isEqualTo(superhero.getSuperheroName());
    }

    @Test
    public void findSuperheroWhichExist() {
        Superhero newSuperhero = new Superhero();
        newSuperhero.setSuperheroName("Wonder Woman");
        newSuperhero.setFirstName("Diana");
        newSuperhero.setLastName("Princess");
        Superhero persistSuperhero = entityManager.persist(newSuperhero);
        entityManager.flush();
        Superhero foundSuperhero = superheroRepository.findById(persistSuperhero.getId()).get();
        Assert.assertEquals(persistSuperhero.getId(), foundSuperhero.getId());
        Assert.assertEquals(persistSuperhero.getFirstName(), foundSuperhero.getFirstName());
        Assert.assertEquals(persistSuperhero.getLastName(), foundSuperhero.getLastName());
        Assert.assertEquals(persistSuperhero.getSuperheroName(), foundSuperhero.getSuperheroName());
        Assert.assertEquals(persistSuperhero.getMissions().size(), foundSuperhero.getMissions().size());
    }
}
