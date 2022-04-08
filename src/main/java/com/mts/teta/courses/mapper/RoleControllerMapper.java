package com.mts.teta.courses.mapper;

import com.mts.teta.courses.domain.Role;
import com.mts.teta.courses.dto.RoleResponse;
import org.springframework.stereotype.Component;

@Component
public class RoleControllerMapper {
    public RoleResponse mapRoleToRoleResponse(Role role, String actionResponse) {
        return new RoleResponse(role.getRoleId(),
                role.getName(),
                actionResponse);
    }
}
