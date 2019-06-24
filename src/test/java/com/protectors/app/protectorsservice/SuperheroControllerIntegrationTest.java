package com.protectors.app.protectorsservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.protectors.app.protectorsservice.api.SuperheroController;
import com.protectors.app.protectorsservice.entity.Superhero;
import com.protectors.app.protectorsservice.service.SuperheroService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(SuperheroController.class)
public class SuperheroControllerIntegrationTest {
    @Autowired
    private MockMvc mvc;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    private SuperheroService superheroService;

    @Before
    public void init() {
        Superhero superhero = new Superhero();
        superhero.setId(1L);
        superhero.setSuperheroName("Superman");
        superhero.setFirstName("Clark");
        superhero.setLastName("Kent");
        when(superheroService.findSuperhero(1L)).thenReturn(Optional.of(superhero));
    }

    @Test
    public void givenSuperhero_whenGetSuperhero_thenReturnJsonArray()
            throws Exception {

        Superhero superhero = new Superhero();
        superhero.setId(1L);
        superhero.setSuperheroName("Superman");
        superhero.setFirstName("Clark");
        superhero.setLastName("Kent");

        given(superheroService.createSuperhero(any(Superhero.class))).willReturn(superhero);

        mvc.perform(post("/superhero")
                .content(objectMapper.writeValueAsString(superhero))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", is("Clark")))
                .andExpect(jsonPath("$.lastName", is("Kent")))
                .andExpect(jsonPath("$.superheroName", is("Superman")));
    }

    @Test
    public void find_superheroByIdNotFound_404() throws Exception {
        mvc.perform(get("/superhero/5")).andExpect(status().isNotFound());
    }

    @Test
    public void find_a_superhero_OK() throws Exception {

        mvc.perform(get("/superhero/1"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.firstName", is("Clark")))
                .andExpect(jsonPath("$.lastName", is("Kent")))
                .andExpect(jsonPath("$.superheroName", is("Superman")));

        verify(superheroService, times(1)).findSuperhero(1L);
    }



}
