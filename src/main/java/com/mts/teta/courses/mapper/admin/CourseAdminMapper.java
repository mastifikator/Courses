package com.mts.teta.courses.mapper.admin;

import com.mts.teta.courses.domain.Course;
import com.mts.teta.courses.dto.admin.AdminCourseResponse;
import com.mts.teta.courses.mapper.UserControllerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.stream.Collectors;

@Component
public class CourseAdminMapper {
    private static final String FOUND_USER_ON_COURSE = "Found user assign to Course";

    @Autowired
    UserControllerMapper userControllerMapper;

    public AdminCourseResponse mapCourseToCourseResponse(Course course, String actionDescription) {

        return new AdminCourseResponse(
                course.getCourseId(),
                course.getAuthor(),
                course.getTitle(),
                course.getDescription(),
                course.getDateCreated(),
                course.getChangeAuthor(),
                course.getDateChanged(),
                course.getTag(),
                actionDescription,
                course.getUsers() == null ? Collections.emptySet() : course.getUsers()
                        .stream()
                        .map(u -> userControllerMapper.mapUserToUserResponse(u, FOUND_USER_ON_COURSE))
                        .collect(Collectors.toSet())
        );
    }
}
