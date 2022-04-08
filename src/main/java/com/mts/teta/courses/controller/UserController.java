package com.mts.teta.courses.controller;

import com.mts.teta.courses.dto.RoleResponse;
import com.mts.teta.courses.dto.UserRequestToCreate;
import com.mts.teta.courses.dto.UserRequestToUpdate;
import com.mts.teta.courses.dto.UserResponse;
import com.mts.teta.courses.mapper.RoleControllerMapper;
import com.mts.teta.courses.mapper.UserControllerMapper;
import com.mts.teta.courses.service.StatisticsCounter;
import com.mts.teta.courses.service.UserLister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("admin/user")
public class UserController {
    private final static String GET_ANSWER = "User found";
    private final static String UPDATE_ANSWER = "User Updated";
    private final static String CREATE_ANSWER = "User Created";
    private final static String DELETE_ANSWER = "User Deleted";
    private final static String ASSIGN_ANSWER = "Role assign to User";
    private final static String UNASSIGNED_ANSWER = "Role unassigned from User";
    private final static String ROLE_ANSWER = "Role found";

    @Autowired
    private StatisticsCounter statisticsCounter;
    @Autowired
    private UserLister userLister;
    @Autowired
    private UserControllerMapper userControllerMapper;
    @Autowired
    private RoleControllerMapper roleControllerMapper;

    @GetMapping("/{userId}")
    public UserResponse getUser(@PathVariable("userId") Long userId) {
        statisticsCounter.countHandlerCall("getUser " + userId);
        return userControllerMapper.mapUserToUserResponse(userLister.userById(userId), GET_ANSWER);
    }

    @PostMapping
    public UserResponse createCourse(@Valid @RequestBody UserRequestToCreate request) {
        statisticsCounter.countHandlerCall(CREATE_ANSWER + request);

        return userControllerMapper
                .mapUserToUserResponse(userLister
                        .createUser(request), CREATE_ANSWER);
    }

    @PutMapping("/{userId}")
    public UserResponse updateUser(@PathVariable Long userId,
                                   @Valid @RequestBody UserRequestToUpdate request) {
        statisticsCounter.countHandlerCall(UPDATE_ANSWER + userId + " " + request);

        return userControllerMapper
                .mapUserToUserResponse(userLister
                        .updateUser(userId, request), UPDATE_ANSWER);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Long userId) {
        statisticsCounter.countHandlerCall(DELETE_ANSWER + userId);
        userLister.deleteUser(userId);
    }

    @PutMapping("/{userId}/roles/{roleId}")
    public UserResponse assignRoleToUser(@PathVariable("userId") Long userId,
                                         @PathVariable("roleId") Long roleId) {
        return userControllerMapper
                .mapUserToUserResponse(userLister
                        .assignedRoleToUser(roleId, userId), ASSIGN_ANSWER);
    }

    @DeleteMapping("/{userId}/roles/{roleId}")
    public UserResponse unassignedRoleFromUser(@PathVariable("userId") Long userId,
                                               @PathVariable("roleId") Long roleId) {
        return userControllerMapper
                .mapUserToUserResponse(userLister
                        .unassignedRoleFromUser(roleId, userId), UNASSIGNED_ANSWER);
    }

    @GetMapping("/{userId}/roles")
    public List<RoleResponse> getRoles(@PathVariable("userId") Long userId) {

        return userLister.getRolesByUserId(userId).stream()
                .map(r -> roleControllerMapper.mapRoleToRoleResponse(r, ROLE_ANSWER))
                .collect(Collectors.toList());
    }
}
