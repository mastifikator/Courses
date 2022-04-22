package com.mts.teta.courses.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mts.teta.courses.domain.Role;
import com.mts.teta.courses.dto.UserRequestToCreate;
import com.mts.teta.courses.dto.UserRequestToUpdate;
import com.mts.teta.courses.mapper.RoleControllerMapper;
import com.mts.teta.courses.mapper.UserControllerMapper;
import com.mts.teta.courses.service.StatisticsCounter;
import com.mts.teta.courses.security.UserAuthService;
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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private StatisticsCounter statisticsCounter;
    @MockBean
    private UserLister userLister;
    @MockBean
    private UserControllerMapper userControllerMapper;
    @MockBean
    private RoleControllerMapper roleControllerMapper;

    @Test
    @WithMockUser(value = "test", roles = {"ADMIN"})
    void getUserShouldReturn200() throws Exception {
        mockMvc.perform(get("/admin/user/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(value = "test", roles = {"ADMIN"})
    void createUserShouldReturn200ifValid() throws Exception {
        UserRequestToCreate userRequestToCreate = new UserRequestToCreate();
        userRequestToCreate.setUsername("newuser");
        userRequestToCreate.setPassword("password");
        userRequestToCreate.setNickname("newuser");
        userRequestToCreate.setEmail("newuser@email.ru");

        List<Role> roles = new ArrayList<>();
        roles.add(new Role("ROLE_STUDENT"));
        userRequestToCreate.setRoles(roles);

        mockMvc.perform(post("/admin/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequestToCreate)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(value = "test", roles = {"ADMIN"})
    void updateUserShouldReturn200ifValid() throws Exception {
        UserRequestToUpdate userRequestToUpdate = new UserRequestToUpdate();
        userRequestToUpdate.setUsername("newuser");
        userRequestToUpdate.setPassword("password");
        userRequestToUpdate.setNickname("newuser");
        userRequestToUpdate.setEmail("newuser@email.ru");

        List<Role> roles = new ArrayList<>();
        roles.add(new Role("ROLE_STUDENT"));
        userRequestToUpdate.setRoles(roles);

        mockMvc.perform(put("/admin/user/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequestToUpdate)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(value = "test", roles = {"ADMIN"})
    void deleteUserShouldReturn200() throws Exception {
        mockMvc.perform(delete("/admin/user/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(value = "test", roles = {"ADMIN"})
    void assignRoleToUserShouldReturn200() throws Exception {
        mockMvc.perform(put("/admin/user/1/roles/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(value = "test", roles = {"ADMIN"})
    void unassignedRoleToUserShouldReturn200() throws Exception {
        mockMvc.perform(delete("/admin/user/1/roles/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(value = "test", roles = {"ADMIN"})
    void getRoles() throws Exception {
        mockMvc.perform(delete("/admin/user/1/roles/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}