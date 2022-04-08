package com.mts.teta.courses.service;

import com.mts.teta.courses.dao.RoleRepository;
import com.mts.teta.courses.dao.UserRepository;
import com.mts.teta.courses.domain.Role;
import com.mts.teta.courses.domain.UserPrincipal;
import com.mts.teta.courses.dto.UserRequestToCreate;
import com.mts.teta.courses.dto.UserRequestToUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserLister {
    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    @Autowired
    public UserLister(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public UserPrincipal userById(Long userId) {
        return userRepository.findById(userId).orElseThrow();
    }

    public UserPrincipal createUser(UserRequestToCreate request) {
        UserPrincipal userPrincipal = new UserPrincipal(request.getUsername());
        userRepository.save(userPrincipal);
        return userPrincipal;
    }

    public UserPrincipal updateUser(Long userId, UserRequestToUpdate request) {
        UserPrincipal userPrincipal = userRepository.getById(userId);
        userPrincipal.setUsername(request.getUsername());
        userRepository.save(userPrincipal);
        return userPrincipal;
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    public UserPrincipal assignedRoleToUser(Long roleId, Long userId) {
        UserPrincipal userPrincipal = userRepository.getById(userId);
        Role role = roleRepository.getById(roleId);

        userPrincipal.getRoles().add(role);
        userRepository.save(userPrincipal);
        return userPrincipal;
    }

    public UserPrincipal unassignedRoleFromUser(Long roleId, Long userId) {
        UserPrincipal userPrincipal = userRepository.getById(userId);
        Role role = roleRepository.getById(roleId);

        userPrincipal.getRoles().remove(role);
        userRepository.save(userPrincipal);
        return userPrincipal;
    }
}
