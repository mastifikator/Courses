package com.mts.teta.courses.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mts.teta.courses.dto.ModuleRequestToCreate;
import com.mts.teta.courses.dto.ModuleRequestToUpdate;
import com.mts.teta.courses.mapper.LessonControllerMapper;
import com.mts.teta.courses.mapper.ModuleControllerMapper;
import com.mts.teta.courses.service.LessonLister;
import com.mts.teta.courses.service.ModuleLister;
import com.mts.teta.courses.service.StatisticsCounter;
import com.mts.teta.courses.security.UserAuthService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ModuleController.class)
class ModuleControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserAuthService userAuthService;

    @MockBean
    private ModuleLister moduleLister;
    @MockBean
    private LessonLister lessonLister;
    @MockBean
    private StatisticsCounter statisticsCounter;
    @MockBean
    private ModuleControllerMapper moduleControllerMapper;
    @MockBean
    private LessonControllerMapper lessonControllerMapper;

    @Test
    @WithMockUser(value = "test")
    void getModuleShouldReturn200() throws Exception {
        mockMvc.perform(get("/module/1"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());

        verify(moduleLister, times(1)).moduleById(1L);
    }

    @Test
    @WithMockUser(value = "test")
    void createModuleShouldReturn200ifValid() throws Exception {
        ModuleRequestToCreate moduleRequestToCreate = new ModuleRequestToCreate();
        moduleRequestToCreate.setTitle("Valid title");
        moduleRequestToCreate.setAuthor("author");
        moduleRequestToCreate.setDescription("description");
        moduleRequestToCreate.setCourseId(1L);

        mockMvc.perform(post("/module")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(moduleRequestToCreate))
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(value = "test")
    void createModuleShouldReturn400ifNotValid() throws Exception {
        ModuleRequestToCreate moduleRequestToCreate = new ModuleRequestToCreate();
        moduleRequestToCreate.setTitle("not valid title");
        moduleRequestToCreate.setAuthor("author");
        moduleRequestToCreate.setDescription("description");
        moduleRequestToCreate.setCourseId(1L);

        mockMvc.perform(post("/module")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(moduleRequestToCreate))
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(value = "test")
    void updateModuleShouldReturn200ifValid() throws Exception {
        ModuleRequestToUpdate moduleRequestToUpdate = new ModuleRequestToUpdate();
        moduleRequestToUpdate.setTitle("Valid title");
        moduleRequestToUpdate.setAuthor("author");
        moduleRequestToUpdate.setDescription("description");

        mockMvc.perform(put("/module/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(moduleRequestToUpdate))
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(value = "test")
    void updateModuleShouldReturn400ifNotValid() throws Exception {
        ModuleRequestToUpdate moduleRequestToUpdate = new ModuleRequestToUpdate();
        moduleRequestToUpdate.setTitle("not valid title");
        moduleRequestToUpdate.setAuthor("author");
        moduleRequestToUpdate.setDescription("description");

        mockMvc.perform(put("/module/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(moduleRequestToUpdate))
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(value = "test")
    void deleteModuleShouldReturn200() throws Exception {
        mockMvc.perform(delete("/module/1")
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(value = "test")
    void getLessonsForModuleShouldReturn200() throws Exception {
        mockMvc.perform(get("/module/1/lessons"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}