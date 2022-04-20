package com.mts.teta.courses.service;

import com.mts.teta.courses.dao.RoleRepository;
import com.mts.teta.courses.dao.UserRepository;
import com.mts.teta.courses.domain.Course;
import com.mts.teta.courses.domain.Role;
import com.mts.teta.courses.domain.UserPrincipal;
import com.mts.teta.courses.dto.UserRequestToCreate;
import com.mts.teta.courses.dto.UserRequestToUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class UserLister {
    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserLister(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserPrincipal userById(Long userId) {
        return userRepository.findById(userId).orElseThrow();
    }

    public UserPrincipal userByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow();
    }

    public UserPrincipal createUser(UserRequestToCreate request) {

        UserPrincipal userPrincipal = new UserPrincipal(request.getUsername(),
                passwordEncoder.encode(request.getPassword()), request.getNickname(), request.getEmail());

        userRepository.save(userPrincipal);
        return userPrincipal;
    }


    public UserPrincipal updateUser(Long userId, UserRequestToUpdate request) {
        UserPrincipal userPrincipal = userRepository.getById(userId);
        userPrincipal.setUsername(request.getUsername());
        userPrincipal.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(userPrincipal);
        return userPrincipal;
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    public UserPrincipal assignedRoleToUser(Long roleId, Long userId) {
        UserPrincipal userPrincipal = userRepository.getById(userId);
        Role role = roleRepository.getById(roleId);

        role.getUsers().add(userPrincipal);
        userPrincipal.getRoles().add(role);
        roleRepository.save(role);

        return saveUser(userPrincipal);
    }

    public UserPrincipal unassignedRoleFromUser(Long roleId, Long userId) {
        UserPrincipal userPrincipal = userRepository.getById(userId);
        Role role = roleRepository.getById(roleId);
        role.getUsers().remove(userPrincipal);

        roleRepository.save(role);
        return userPrincipal;
    }

    public Set<Role> getRolesByUserId(Long userId) {
        return userRepository.getById(userId).getRoles();
    }

    public UserPrincipal saveUser(UserPrincipal user) {
        return userRepository.saveAndFlush(user);
    }
}
