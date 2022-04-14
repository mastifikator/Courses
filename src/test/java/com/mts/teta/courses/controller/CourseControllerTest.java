package com.mts.teta.courses.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mts.teta.courses.dto.CourseRequestToCreate;
import com.mts.teta.courses.dto.CourseRequestToUpdate;
import com.mts.teta.courses.handler.CustomAccessDeniedHandler;
import com.mts.teta.courses.mapper.CourseControllerMapper;
import com.mts.teta.courses.mapper.UserControllerMapper;
import com.mts.teta.courses.service.CourseLister;
import com.mts.teta.courses.service.LessonLister;
import com.mts.teta.courses.service.StatisticsCounter;
import com.mts.teta.courses.service.UserAuthService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
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
@WebMvcTest(CourseController.class)
class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserAuthService userAuthService;
    @MockBean
    private CustomAccessDeniedHandler customAccessDeniedHandler;

    @MockBean
    private CourseLister courseLister;
    @MockBean
    private LessonLister lessonLister;
    @MockBean
    private StatisticsCounter statisticsCounter;
    @MockBean
    private CourseControllerMapper courseControllerMapper;
    @MockBean
    private UserControllerMapper userControllerMapper;

    @Test
    @WithMockUser(value = "test")
    void getCourseShouldReturn200() throws Exception {
        mockMvc.perform(get("/course/1"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());

        verify(courseLister, times(1)).courseById(1L);
    }

    @Test
    @WithMockUser(value = "test")
    void getCoursesByTitlePrefixShouldReturn200() throws Exception {
        mockMvc.perform(get("/course/filteredCourses")
                        .requestAttr("prefix", String.class))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());

        verify(courseLister, times(1)).coursesByTitlePrefix(Mockito.any());
    }

    @Test
    @WithMockUser(value = "test")
    void createCourseShouldReturn200IfValid() throws Exception {
        CourseRequestToCreate courseRequestToCreate = new CourseRequestToCreate();
        courseRequestToCreate.setTitle("Valid course title");
        courseRequestToCreate.setAuthor("author");

        mockMvc.perform(post("/course")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(courseRequestToCreate))
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithMockUser(value = "test")
    void createCourseShouldReturn400IfNotValid() throws Exception {
        CourseRequestToCreate courseRequestToCreate = new CourseRequestToCreate();
        courseRequestToCreate.setTitle("not valid title");
        courseRequestToCreate.setAuthor("author");

        mockMvc.perform(post("/course")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(courseRequestToCreate))
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(value = "test")
    void updateCourseShouldReturn200IfValid() throws Exception {
        CourseRequestToUpdate courseRequestToUpdate = new CourseRequestToUpdate();
        courseRequestToUpdate.setTitle("Valid title");
        courseRequestToUpdate.setAuthor("author");

        mockMvc.perform(put("/course/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(courseRequestToUpdate))
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithMockUser(value = "test")
    void updateCourseShouldReturn400IfNotValid() throws Exception {
        CourseRequestToUpdate courseRequestToUpdate = new CourseRequestToUpdate();
        courseRequestToUpdate.setTitle("not valid title");
        courseRequestToUpdate.setAuthor("author");

        mockMvc.perform(put("/course/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(courseRequestToUpdate))
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(value = "test")
    void deleteCourse() throws Exception {
        mockMvc.perform(delete("/course/1")
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithMockUser(value = "test")
    void getUsersFromCourseShouldReturn200() throws Exception {
        mockMvc.perform(get("/course/1/users"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithMockUser(value = "test")
    void assignUserToCourseShouldReturn200() throws Exception {
        mockMvc.perform(put("/course/1/users/1"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithMockUser(value = "test")
    void unassignedUserToCourseShouldReturn200() throws Exception {
        mockMvc.perform(delete("/course/1/users/1"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }
}