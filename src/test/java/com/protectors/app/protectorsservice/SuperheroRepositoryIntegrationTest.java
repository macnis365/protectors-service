package com.protectors.app.protectorsservice;

import com.protectors.app.protectorsservice.entity.Superhero;
import com.protectors.app.protectorsservice.repository.SuperheroRepository;
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

    @Test
    public void whenFindByName_thenReturnEmployee() {
        // given
        Superhero superhero = new Superhero();
        superhero.setSuperheroName("Superman");
        superhero.setFirstName("Clark");
        superhero.setLastName("Kent");
        entityManager.persist(superhero);
        entityManager.flush();

        // when
        Superhero found = superheroRepository.save(superhero);

        // then
        assertThat(found.getSuperheroName())
                .isEqualTo(superhero.getSuperheroName());
    }
}
