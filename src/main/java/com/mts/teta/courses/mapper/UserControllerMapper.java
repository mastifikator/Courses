package com.mts.teta.courses.mapper;

import com.mts.teta.courses.domain.User;
import com.mts.teta.courses.dto.UserResponse;
import org.springframework.stereotype.Component;

@Component
public class UserControllerMapper {
    public UserResponse mapUserToUserResponse(User user, String actionResponse) {
        return new UserResponse(user.getUser_id(),
                user.getUsername(),
                actionResponse);
    }
}
