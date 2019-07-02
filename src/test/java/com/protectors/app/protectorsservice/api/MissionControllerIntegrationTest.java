package com.protectors.app.protectorsservice.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.protectors.app.protectorsservice.customexception.ActiveMissionCannotDelete;
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
        Mission mission = new Mission.MissionBuilder().setId(10L).setName("Life Code").setCompleted(true).setDeleted(false).build();
        when(missionService.findMission(10L)).thenReturn(mission);
    }

    @Test
    public void createMissionWithoutNameAndReturn400() throws Exception {
        Mission missionToPersist = new Mission.MissionBuilder().setId(1L).setCompleted(false).setDeleted(false).build();
        given(missionService.createMission(any(Mission.class))).willReturn(missionToPersist);
        mvc.perform(post("/mission")
                .content(objectMapper.writeValueAsString(missionToPersist))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createMissionAndReturn200() throws Exception {
        Mission missionToPersist = new Mission.MissionBuilder().setName("My mission").setId(1L).setCompleted(true).setDeleted(false).build();
        given(missionService.createMission(any(Mission.class))).willReturn(missionToPersist);
        mvc.perform(post("/mission")
                .content(objectMapper.writeValueAsString(missionToPersist))
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
        Mission updateMission = new Mission.MissionBuilder().setName("godspeed").setId(2L).setCompleted(false).setDeleted(false).build();
        given(missionService.update(any(Long.class), any(Mission.class))).willReturn(updateMission);
        mvc.perform(put("/mission/2", updateMission)
                .content(objectMapper.writeValueAsString(updateMission))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("godspeed")))
                .andExpect(jsonPath("$.deleted", is(false)))
                .andExpect(jsonPath("$.completed", is(false)));

    }

    @Test
    public void updateMissionAndReturnNok() throws Exception {
        Mission updateMission = new Mission.MissionBuilder().setName("godspeed").setId(2L).setCompleted(true).setDeleted(true).build();
        when(missionService.update(anyLong(), any(Mission.class))).thenThrow(new ActiveMissionCannotDelete(2L));
        mvc.perform(put("/mission/2", updateMission)
                .content(objectMapper.writeValueAsString(updateMission))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

    @Test
    public void deleteMissionByIdNotFound404() throws Exception {
        doThrow(new MissionNotFound(2L)).doNothing().when(missionService).softDeleteMission(anyLong());
        mvc.perform(delete("/mission/2")).andExpect(status().isNotFound());
    }

    @Test
    public void deleteMissionByIdNotFound200() throws Exception {
        mvc.perform(delete("/mission/10")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

    }
}
