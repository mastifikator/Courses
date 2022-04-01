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

    @GetMapping("/{id}")
    public UserResponse getUser(@PathVariable("id") Long id) {
        statisticsCounter.countHandlerCall("getUser " + id);
        return userControllerMapper.mapUserToUserResponse(userLister.userById(id), GET_ANSWER);
    }

    @PostMapping
    public UserResponse createCourse(@Valid @RequestBody UserRequestToCreate request) {
        statisticsCounter.countHandlerCall(CREATE_ANSWER + request);

        return userControllerMapper
                .mapUserToUserResponse(userLister
                        .createUser(request), CREATE_ANSWER);
    }

    @PutMapping("/{id}")
    public UserResponse updateUser(@PathVariable Long id,
                                   @Valid @RequestBody UserRequestToUpdate request) {
        statisticsCounter.countHandlerCall(UPDATE_ANSWER + id + " " + request);

        return userControllerMapper
                .mapUserToUserResponse(userLister
                        .updateUser(id, request), UPDATE_ANSWER);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        statisticsCounter.countHandlerCall(DELETE_ANSWER + id);
        userLister.deleteUser(id);
    }
}
