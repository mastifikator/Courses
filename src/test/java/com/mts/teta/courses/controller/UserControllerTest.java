package com.mts.teta.courses.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mts.teta.courses.dto.ModuleRequestToCreate;
import com.mts.teta.courses.dto.UserRequestToCreate;
import com.mts.teta.courses.dto.UserRequestToUpdate;
import com.mts.teta.courses.handler.CustomAccessDeniedHandler;
import com.mts.teta.courses.mapper.RoleControllerMapper;
import com.mts.teta.courses.mapper.UserControllerMapper;
import com.mts.teta.courses.service.StatisticsCounter;
import com.mts.teta.courses.service.UserAuthService;
import com.mts.teta.courses.service.UserLister;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserAuthService userAuthService;
    @MockBean
    private CustomAccessDeniedHandler customAccessDeniedHandler;

    @MockBean
    private StatisticsCounter statisticsCounter;
    @MockBean
    private UserLister userLister;
    @MockBean
    private UserControllerMapper userControllerMapper;
    @MockBean
    private RoleControllerMapper roleControllerMapper;

    @Test
    @WithMockUser(value = "test")
    void getUserShouldReturn200() throws Exception {
        mockMvc.perform(get("/admin/user/1"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithMockUser(value = "test")
    void createUserShouldReturn200ifValid() throws Exception {
        UserRequestToCreate userRequestToCreate = new UserRequestToCreate();
        userRequestToCreate.setUsername("user");
        userRequestToCreate.setPassword("password");

        mockMvc.perform(post("/admin/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequestToCreate))
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithMockUser(value = "test")
    void updateUserShouldReturn200ifValid() throws Exception {
        UserRequestToUpdate userRequestToUpdate = new UserRequestToUpdate();
        userRequestToUpdate.setUsername("user");
        userRequestToUpdate.setPassword("password");

        mockMvc.perform(put("/admin/user/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequestToUpdate))
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithMockUser(value = "test")
    void deleteUserShouldReturn200() throws Exception {
        mockMvc.perform(delete("/admin/user/1")
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithMockUser(value = "test")
    void assignRoleToUserShouldReturn200() throws Exception {
        mockMvc.perform(put("/admin/user/1/roles/1")
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithMockUser(value = "test")
    void unassignedRoleToUserShouldReturn200() throws Exception {
        mockMvc.perform(delete("/admin/user/1/roles/1")
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithMockUser(value = "test")
    void getRoles() throws Exception {
        mockMvc.perform(delete("/admin/user/1/roles/1"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }
}