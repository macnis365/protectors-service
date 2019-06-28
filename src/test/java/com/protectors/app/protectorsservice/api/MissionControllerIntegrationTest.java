package com.protectors.app.protectorsservice.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.protectors.app.protectorsservice.customexception.CompletedMissionCannotDelete;
import com.protectors.app.protectorsservice.customexception.MissionNotFound;
import com.protectors.app.protectorsservice.entity.Mission;
import com.protectors.app.protectorsservice.service.MissionService;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(MissionController.class)
public class MissionControllerIntegrationTest {
    @Autowired
    private MockMvc mvc;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    private MissionService missionService;

    @Before
    public void init() {
        Mission mission = new Mission();
        mission.setId(10L);
        mission.setName("Life Code");
        mission.setCompleted(true);
        mission.setDeleted(false);
        when(missionService.findMission(10L)).thenReturn(mission);
    }

    @Test
    public void createMissionWithoutNameAndReturn400() throws Exception {
        Mission mission = new Mission();
        mission.setId(1L);
        mission.setCompleted(false);
        mission.setDeleted(false);

        given(missionService.createMission(any(Mission.class))).willReturn(mission);

        mvc.perform(post("/mission")
                .content(objectMapper.writeValueAsString(mission))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createMissionAndReturn200() throws Exception {
        Mission mission = new Mission();
        mission.setId(1L);
        mission.setName("My mission");
        mission.setCompleted(true);
        mission.setDeleted(false);

        given(missionService.createMission(any(Mission.class))).willReturn(mission);

        mvc.perform(post("/mission")
                .content(objectMapper.writeValueAsString(mission))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("My mission")))
                .andExpect(jsonPath("$.deleted", is(false)))
                .andExpect(jsonPath("$.completed", is(true)));
    }

    @Test
    public void findMissionByIdNotFound404() throws Exception {
        given(missionService.findMission(anyLong())).willThrow(new MissionNotFound(5L));
        mvc.perform(get("/mission/5")).andExpect(status().isNotFound());
    }

    @Test
    public void findMissionWhichExistOK() throws Exception {

        mvc.perform(get("/mission/10"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(10)))
                .andExpect(jsonPath("$.name", is("Life Code")))
                .andExpect(jsonPath("$.deleted", is(false)))
                .andExpect(jsonPath("$.completed", is(true)));

        verify(missionService, times(1)).findMission(10L);
    }

    @Test
    public void updateMissionAndReturnOk() throws Exception {
        Mission mission = new Mission();
        mission.setId(2L);
        mission.setName("godspeed");
        mission.setCompleted(false);
        mission.setDeleted(false);
        given(missionService.update(any(Long.class), any(Mission.class))).willReturn(mission);

        mvc.perform(put("/mission/2", mission)
                .content(objectMapper.writeValueAsString(mission))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("godspeed")))
                .andExpect(jsonPath("$.deleted", is(false)))
                .andExpect(jsonPath("$.completed", is(false)));

    }

    @Test
    public void updateMissionAndReturnNok() throws Exception {
        Mission mission = new Mission();
        mission.setId(2L);
        mission.setName("godspeed");
        mission.setCompleted(true);
        mission.setDeleted(true);
        when(missionService.update(anyLong(), any(Mission.class))).thenThrow(new CompletedMissionCannotDelete(2L));

        mvc.perform(put("/mission/2", mission)
                .content(objectMapper.writeValueAsString(mission))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

    @Test
    public void deleteMissionByIdNotFound404() throws Exception {
        when(missionService.softDeleteMission(anyLong())).thenThrow(new MissionNotFound(2L));
        mvc.perform(delete("/mission/2")).andExpect(status().isNotFound());
    }

    @Test
    public void deleteMissionByIdNotFound200() throws Exception {
        Mission mission = new Mission();
        mission.setId(2L);
        mission.setName("godspeed");
        mission.setCompleted(true);
        mission.setDeleted(true);
        given(missionService.softDeleteMission(any(Long.class))).willReturn(mission);

        mvc.perform(delete("/mission/2")
                .content(objectMapper.writeValueAsString(mission))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("godspeed")))
                .andExpect(jsonPath("$.deleted", is(true)))
                .andExpect(jsonPath("$.completed", is(true)));

    }
}