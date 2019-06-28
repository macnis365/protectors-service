package com.protectors.app.protectorsservice.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.protectors.app.protectorsservice.customexception.SuperheroNotFound;
import com.protectors.app.protectorsservice.entity.Mission;
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

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
        when(superheroService.findSuperhero(1L)).thenReturn(superhero);
    }

    @Test
    public void updateSuperheroAndReturn200() throws Exception {

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
    public void createSuperheroWithoutSuperheroNameAndReturn400() throws Exception {

        Superhero superhero = new Superhero();
        superhero.setId(1L);
        superhero.setSuperheroName("");
        superhero.setFirstName("Clark");
        superhero.setLastName("Kent");

        given(superheroService.createSuperhero(any(Superhero.class))).willReturn(superhero);

        mvc.perform(post("/superhero")
                .content(objectMapper.writeValueAsString(superhero))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }


    @Test
    public void findSuperheroByIdNotFound404() throws Exception {
        given(superheroService.findSuperhero(anyLong())).willThrow(new SuperheroNotFound(5L));
        mvc.perform(get("/superhero/5")).andExpect(status().isNotFound());
    }


    @Test
    public void findASuperheroWhichExistOK() throws Exception {

        mvc.perform(get("/superhero/1"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.firstName", is("Clark")))
                .andExpect(jsonPath("$.lastName", is("Kent")))
                .andExpect(jsonPath("$.superheroName", is("Superman")));

        verify(superheroService, times(1)).findSuperhero(1L);
    }

    @Test
    public void updateSuperheroWithMissionAndReturn() throws Exception {
        Superhero superhero = new Superhero();
        superhero.setId(1L);
        superhero.setSuperheroName("Superman");
        superhero.setFirstName("Clark");
        superhero.setLastName("Kent");
        Mission mission = new Mission();
        mission.setId(2L);
        mission.setName("godspeed");
        mission.setCompleted(false);
        mission.setDeleted(false);
        superhero.getMissions().add(mission);

        given(superheroService.updateSuperhero(any(Long.class), any(Superhero.class))).willReturn(superhero);

        mvc.perform(put("/superhero/1", superhero)
                .content(objectMapper.writeValueAsString(superhero))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is("Clark")))
                .andExpect(jsonPath("$.lastName", is("Kent")))
                .andExpect(jsonPath("$.superheroName", is("Superman")))
                .andExpect(jsonPath("$.missions[0].name", is("godspeed")))
                .andExpect(jsonPath("$.missions[0].deleted", is(false)))
                .andExpect(jsonPath("$.missions[0].completed", is(false)));

    }

    @Test
    public void deleteSuperheroByIdNotFound404() throws Exception {
        doThrow(new SuperheroNotFound(5L)).doNothing().when(superheroService).deleteSuperhero(anyLong());
        mvc.perform(delete("/superhero/5")).andExpect(status().isNotFound());
    }


}
