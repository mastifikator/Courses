package com.mts.teta.courses.service;

import com.mts.teta.courses.domain.UserPrincipal;
import com.mts.teta.courses.dto.UserRequestToCreate;
import com.mts.teta.courses.dto.UserRequestToUpdate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

class UserListerTest extends PreparedDatabase {

    @Autowired
    UserLister userLister;

    @Test
    @Transactional
    void userById() {
        assertEquals("user1", userLister.userById(1L).getUsername());
    }

    @Test
    @Transactional
    void createUser() {
        UserRequestToCreate userRequestToCreate = new UserRequestToCreate();
        userRequestToCreate.setUsername("user4");
        userRequestToCreate.setPassword("pass4");

        UserPrincipal userPrincipal = userLister.createUser(userRequestToCreate);

        assertTrue(userRepository.findAll().contains(userPrincipal));
    }

    @Test
    @Transactional
    void updateUser() {
        UserRequestToUpdate userRequestToUpdate = new UserRequestToUpdate();
        userRequestToUpdate.setUsername("user3_new");
        userRequestToUpdate.setPassword("pass3_new");
        userRequestToUpdate.setNickname("nickname");
        userRequestToUpdate.setEmail("email");

        UserPrincipal userPrincipal = userLister.updateUser(3L, userRequestToUpdate);

        assertEquals(userPrincipal.getUsername(), userLister.userById(3L).getUsername());
    }

    @Test
    @Transactional
    void deleteUser() {
        UserRequestToCreate userRequestToCreate = new UserRequestToCreate();
        userRequestToCreate.setUsername("user5");
        userRequestToCreate.setPassword("pass5");

        int countUserBeforeAdd = userRepository.findAll().size();
        UserPrincipal userPrincipal = userLister.createUser(userRequestToCreate);
        userLister.deleteUser(userPrincipal.getUserId());

        assertEquals(countUserBeforeAdd, userRepository.findAll().size());
    }

    @Test
    @Transactional
    void assignedRoleToUser() {
        userLister.assignedRoleToUser(1L, 1L);

        assertEquals(1, roleRepository.findById(1L).orElseThrow().getUsers().size());
    }

    @Test
    @Transactional
    void unassignedRoleFromUser() {
        int countRolesBeforeAssigned = roleRepository.findById(1L).orElseThrow().getUsers().size();

        userLister.assignedRoleToUser(2L, 2L);
        userLister.unassignedRoleFromUser(2L, 2L);

        assertEquals(countRolesBeforeAssigned, roleRepository.findById(2L).orElseThrow().getUsers().size());
    }

    @Test
    @Transactional
    void getRolesByUserId() {
        userLister.assignedRoleToUser(3L, 3L);

        assertEquals(1, roleRepository.findById(3L).orElseThrow().getUsers().size());
    }
}