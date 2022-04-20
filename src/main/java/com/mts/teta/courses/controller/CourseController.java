package com.mts.teta.courses.controller;

import com.mts.teta.courses.dto.CourseRequestToCreate;
import com.mts.teta.courses.dto.CourseRequestToUpdate;
import com.mts.teta.courses.dto.CourseResponse;
import com.mts.teta.courses.dto.UserResponse;
import com.mts.teta.courses.mapper.CourseControllerMapper;
import com.mts.teta.courses.mapper.UserControllerMapper;
import com.mts.teta.courses.service.CourseLister;
import com.mts.teta.courses.service.StatisticsCounter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class CourseController {
    private final static String GET_ANSWER = "Course found";
    private final static String UPDATE_ANSWER = "Course Updated";
    private final static String CREATE_ANSWER = "Course Created";
    private final static String FILTER_ANSWER = "Course Filtered";
    private final static String DELETE_ANSWER = "Course Deleted";
    private final static String ASSIGN_ANSWER = "User Assign to course";
    private final static String UNASSIGNED_ANSWER = "User unassigned from course";
    private final static String USERS_FOUND_ANSWER = "Users found";

    @Autowired
    private CourseLister courseLister;
    @Autowired
    private StatisticsCounter statisticsCounter;
    @Autowired
    private CourseControllerMapper courseControllerMapper;
    @Autowired
    private UserControllerMapper userControllerMapper;

    @GetMapping("/course/{courseId}")
    public CourseResponse getCourse(@PathVariable("courseId") Long courseId) {
        statisticsCounter.countHandlerCall("getCourse " + courseId);
        return courseControllerMapper.mapCourseToCourseResponse(courseLister.courseById(courseId), GET_ANSWER);
    }

    @GetMapping("/course/filteredCourses")
    public List<CourseResponse> getCoursesByTitlePrefix(@RequestParam(value = "titlePrefix", required = false) String titlePrefix) {
        statisticsCounter.countHandlerCall(FILTER_ANSWER + titlePrefix);

        return courseLister
                .coursesByTitlePrefix(titlePrefix)
                .stream()
                .map(c -> courseControllerMapper.mapCourseToCourseResponse(c, FILTER_ANSWER))
                .collect(Collectors.toList());
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/course")
    public CourseResponse createCourse(@Valid @RequestBody CourseRequestToCreate request) {
        statisticsCounter.countHandlerCall(CREATE_ANSWER + request);

        return courseControllerMapper
                .mapCourseToCourseResponse(courseLister
                        .createCourse(request), CREATE_ANSWER);
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("/course/{courseId}")
    public CourseResponse updateCourse(@PathVariable Long courseId,
                                       @Valid @RequestBody CourseRequestToUpdate request) {
        statisticsCounter.countHandlerCall(UPDATE_ANSWER + courseId + " " + request);

        return courseControllerMapper
                .mapCourseToCourseResponse(courseLister
                        .updateCourse(courseId, request), UPDATE_ANSWER);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/course/{courseId}")
    public void deleteCourse(@PathVariable Long courseId) {
        statisticsCounter.countHandlerCall(DELETE_ANSWER + courseId);
        courseLister.deleteCourse(courseId);
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/course/{courseId}/users")
    public Set<UserResponse> getUsersFromCourse(@PathVariable Long courseId) {
        statisticsCounter.countHandlerCall(USERS_FOUND_ANSWER + courseId);

        return courseLister.getUsersFromCourse(courseId)
                .stream()
                .map(u -> userControllerMapper.mapUserToUserResponse(u, USERS_FOUND_ANSWER))
                .collect(Collectors.toSet());
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("/course/{courseId}/users/{userId}")
    public CourseResponse assignUserToCourse(@PathVariable("courseId") Long courseId,
                                             @PathVariable("userId") Long userId) {

        return courseControllerMapper
                .mapCourseToCourseResponse(courseLister
                        .assignedUserToCourse(courseId, userId), ASSIGN_ANSWER);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/course/{courseId}/users/{userId}")
    public CourseResponse unassignedUserFromCourse(@PathVariable("courseId") Long courseId,
                                                   @PathVariable("userId") Long userId) {

        return courseControllerMapper
                .mapCourseToCourseResponse(courseLister
                        .unassignedUserToCourse(courseId, userId), UNASSIGNED_ANSWER);
    }

}
