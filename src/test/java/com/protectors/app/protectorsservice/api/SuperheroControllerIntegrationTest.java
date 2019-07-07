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

import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        Superhero superhero = new Superhero.SuperheroBuilder().setId(1L).setSuperheroName("Superman").setFirstName("Clark").setLastName("Kent").build();
        when(superheroService.findSuperhero(1L)).thenReturn(superhero);
    }

    @Test
    public void updateSuperheroAndReturn200() throws Exception {
        Superhero createSuperhero = new Superhero.SuperheroBuilder().setId(1L).setSuperheroName("Superman").setFirstName("Clark").setLastName("Kent").build();
        given(superheroService.createSuperhero(any(Superhero.class))).willReturn(createSuperhero);
        mvc.perform(post("/superhero")
                .content(objectMapper.writeValueAsString(createSuperhero))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", is("Clark")))
                .andExpect(jsonPath("$.lastName", is("Kent")))
                .andExpect(jsonPath("$.superheroName", is("Superman")));
    }

    @Test
    public void createSuperheroWithoutSuperheroNameAndReturn400() throws Exception {

        Superhero createSuperhero = new Superhero.SuperheroBuilder().setId(1L).setFirstName("Clark").setLastName("Kent").build();
        given(superheroService.createSuperhero(any(Superhero.class))).willReturn(createSuperhero);
        mvc.perform(post("/superhero")
                .content(objectMapper.writeValueAsString(createSuperhero))
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
        Mission updateMission = new Mission.MissionBuilder().setId(2L).setName("godspeed").setCompleted(false).setDeleted(false).build();
        Superhero superhero = new Superhero.SuperheroBuilder().setId(1L).setSuperheroName("Superman").setFirstName("Clark").setLastName("Kent").setMissions(Stream.of(updateMission).collect(Collectors.toSet())).build();
        given(superheroService.updateSuperhero(any(Long.class), any(Superhero.class))).willReturn(superhero);
        mvc.perform(put("/superhero/1", superhero)
                .content(objectMapper.writeValueAsString(superhero))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is("Clark")))
                .andExpect(jsonPath("$.lastName", is("Kent")))
                .andExpect(jsonPath("$.superheroName", is("Superman")))
                .andExpect(jsonPath("$.missions[0].name", is("godspeed")))
                .andExpect(jsonPath("$.missions[0].completed", is(false)));

    }

    @Test
    public void deleteSuperheroByIdNotFound404() throws Exception {
        doThrow(new SuperheroNotFound(5L)).doNothing().when(superheroService).deleteSuperhero(anyLong());
        mvc.perform(delete("/superhero/5")).andExpect(status().isNotFound());
    }
}
