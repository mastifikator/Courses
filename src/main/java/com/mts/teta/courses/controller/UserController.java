package com.mts.teta.courses.controller;

import com.mts.teta.courses.dto.UserRequestToCreate;
import com.mts.teta.courses.dto.UserRequestToUpdate;
import com.mts.teta.courses.dto.UserResponse;
import com.mts.teta.courses.mapper.UserControllerMapper;
import com.mts.teta.courses.service.StatisticsCounter;
import com.mts.teta.courses.service.UserLister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {
    private final static String GET_ANSWER = "User found";
    private final static String UPDATE_ANSWER = "User Updated";
    private final static String CREATE_ANSWER = "User Created";
    private final static String DELETE_ANSWER = "User Deleted";

    @Autowired
    private StatisticsCounter statisticsCounter;
    @Autowired
    private UserLister userLister;
    @Autowired
    private UserControllerMapper userControllerMapper;

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
}
