package com.mts.teta.courses.service;

import com.mts.teta.courses.dao.RoleRepository;
import com.mts.teta.courses.dao.UserRepository;
import com.mts.teta.courses.domain.Role;
import com.mts.teta.courses.domain.UserPrincipal;
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
public class RoleLister {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleLister(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

}
