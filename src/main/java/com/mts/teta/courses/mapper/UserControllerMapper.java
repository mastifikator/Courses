package com.mts.teta.courses.mapper;

import com.mts.teta.courses.domain.UserPrincipal;
import com.mts.teta.courses.dto.UserResponse;
import org.springframework.stereotype.Component;

@Component
public class UserControllerMapper {
    public UserResponse mapUserToUserResponse(UserPrincipal userPrincipal, String actionResponse) {
        return new UserResponse(userPrincipal.getUserId(),
                userPrincipal.getUsername(),
                userPrincipal.getNickname(),
                userPrincipal.getEmail(),
                actionResponse);
    }
}
