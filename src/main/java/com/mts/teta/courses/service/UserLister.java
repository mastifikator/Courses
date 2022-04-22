package com.mts.teta.courses.service;

import com.mts.teta.courses.dao.RoleRepository;
import com.mts.teta.courses.dao.UserRepository;
import com.mts.teta.courses.domain.Role;
import com.mts.teta.courses.domain.UserPrincipal;
import com.mts.teta.courses.dto.UI.RequestToRegistration;
import com.mts.teta.courses.dto.UI.UserRequestToChange;
import com.mts.teta.courses.dto.UserRequestToCreate;
import com.mts.teta.courses.dto.UserRequestToUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;
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

        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already exist!");
        }

        UserPrincipal userPrincipal = new UserPrincipal(request.getUsername(),
                passwordEncoder.encode(request.getPassword()),
                request.getNickname(),
                request.getEmail());

        userRepository.save(userPrincipal);

        for (Role role : request.getRoles()) {
            assignedRoleToUser(role.getRoleId(), userPrincipal.getUserId());
        }

        return userPrincipal;
    }


    public UserPrincipal updateUser(Long userId, UserRequestToUpdate request) {
        UserPrincipal userPrincipal = userRepository.getById(userId);

        if (!request.getUsername().equals("")) {
            userPrincipal.setUsername(request.getUsername());
        }
        if (!request.getNickname().equals("")) {
            userPrincipal.setNickname(request.getNickname());
        }
        if (!request.getEmail().equals("")) {
            userPrincipal.setEmail(request.getEmail());
        }
        if (!request.getPassword().equals("")) {
            userPrincipal.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        userPrincipal.setChangedDate(new Timestamp(System.currentTimeMillis()));

        userRepository.save(userPrincipal);

        unassignedAllRoleFromUser(userPrincipal.getUserId());

        if (request.getRoles() != null) {
            for (Role role : request.getRoles()) {
                assignedRoleToUser(role.getRoleId(), userPrincipal.getUserId());
            }
        }

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

    public void unassignedAllRoleFromUser(Long userId) {
        Set<Role> allUserRoles = getRolesByUserId(userId);

        for (Role role : allUserRoles) {
            unassignedRoleFromUser(role.getRoleId(), userId);
        }
    }

    public boolean isStudent(UserPrincipal user) {
        boolean result = false;

        for (Role role : user.getRoles()) {
            if (role.getName().equals("ROLE_STUDENT")) {
                result = true;
            }
        }

        return result;
    }

    public List<UserPrincipal> getAllUsers() {
        return userRepository.findAll();
    }

    public Set<Role> getRolesByUserId(Long userId) {
        return userRepository.getById(userId).getRoles();
    }

    public UserPrincipal saveUser(UserPrincipal user) {
        return userRepository.saveAndFlush(user);
    }

    //Methods for UI

    public void changeUserFromUI(Long userId, UserRequestToChange userRequestToChange) {
        UserPrincipal userPrincipal = userRepository.getById(userId);

        if (!userRequestToChange.getUsername().equals("")) {
            userPrincipal.setUsername(userRequestToChange.getUsername());
        }

        if (!userRequestToChange.getNickname().equals("")) {
            userPrincipal.setNickname(userRequestToChange.getNickname());
        }

        if (!userRequestToChange.getEmail().equals("")) {
            userPrincipal.setEmail(userRequestToChange.getEmail());
        }

        if (!userRequestToChange.getPassword().equals("")) {
            userPrincipal.setPassword(passwordEncoder.encode(userRequestToChange.getPassword()));
        }

        userPrincipal.setChangedDate(new Timestamp(System.currentTimeMillis()));
        userPrincipal.setChangeAuthor(userById(userId).getUsername());

        userRepository.save(userPrincipal);
    }

    public UserPrincipal createUserFromRegistration(RequestToRegistration request) {

        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already exist!");
        }

        UserPrincipal userPrincipal = new UserPrincipal(request.getUsername(),
                passwordEncoder.encode(request.getPassword()),
                request.getNickname(),
                request.getEmail());

        userRepository.save(userPrincipal);
        assignedRoleToUser(1L, userPrincipal.getUserId());

        return userPrincipal;
    }

}
