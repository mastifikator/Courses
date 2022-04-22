package com.mts.teta.courses.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mts.teta.courses.dto.LessonRequestToCreate;
import com.mts.teta.courses.dto.LessonRequestToUpdate;
import com.mts.teta.courses.mapper.LessonControllerMapper;
import com.mts.teta.courses.service.LessonLister;
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
@WebMvcTest(LessonController.class)
class LessonControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserAuthService userAuthService;

    @MockBean
    private LessonLister lessonLister;
    @MockBean
    private StatisticsCounter statisticsCounter;
    @MockBean
    private LessonControllerMapper lessonControllerMapper;

    @Test
    @WithMockUser(value = "test")
    void getLessonShouldReturn200() throws Exception {
        mockMvc.perform(get("/lesson/1"))
                .andDo(print())
                .andExpect(status().isOk());

        verify(lessonLister, times(1)).lessonById(1L);
    }

    @Test
    @WithMockUser(value = "test")
    void createLessonShouldReturn200ifValid() throws Exception {
        LessonRequestToCreate lessonRequestToCreate = new LessonRequestToCreate();
        lessonRequestToCreate.setTitle("Valid title");
        lessonRequestToCreate.setText("text");
        lessonRequestToCreate.setAuthor("author");
        lessonRequestToCreate.setModuleId(1L);

        mockMvc.perform(post("/lesson")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(lessonRequestToCreate))
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(value = "test")
    void createLessonShouldReturn400ifNotValid() throws Exception {
        LessonRequestToCreate lessonRequestToCreate = new LessonRequestToCreate();
        lessonRequestToCreate.setTitle("not valid title");
        lessonRequestToCreate.setText("text");
        lessonRequestToCreate.setModuleId(1L);

        mockMvc.perform(post("/lesson")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(lessonRequestToCreate))
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(value = "test")
    void updateLessonShouldReturn200ifValid() throws Exception {
        LessonRequestToUpdate lessonRequestToUpdate = new LessonRequestToUpdate();
        lessonRequestToUpdate.setTitle("Valid title");
        lessonRequestToUpdate.setText("text");
        lessonRequestToUpdate.setAuthor("author");

        mockMvc.perform(put("/lesson/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(lessonRequestToUpdate))
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(value = "test")
    void updateLessonShouldReturn400ifNotValid() throws Exception {
        LessonRequestToUpdate lessonRequestToUpdate = new LessonRequestToUpdate();
        lessonRequestToUpdate.setTitle("not valid title");
        lessonRequestToUpdate.setText("text");

        mockMvc.perform(put("/lesson/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(lessonRequestToUpdate))
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(value = "test")
    void deleteLessonShouldReturn200() throws Exception {
        mockMvc.perform(delete("/lesson/1")
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk());
    }
}