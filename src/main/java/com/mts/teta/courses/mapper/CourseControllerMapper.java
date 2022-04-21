package com.mts.teta.courses.mapper;

import com.mts.teta.courses.domain.Course;
import com.mts.teta.courses.dto.CourseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.stream.Collectors;

@Component
public class CourseControllerMapper {
    private static final String FOUND_USER_ON_COURSE = "Found user assign to Course";

    @Autowired
    UserControllerMapper userControllerMapper;

    public CourseResponse mapCourseToCourseResponse(Course course, String actionDescription) {

        return new CourseResponse(course.getCourseId(),
                course.getAuthor(),
                course.getTitle(),
                course.getDescription(),
                course.getTag(),
                actionDescription,
                course.getUsers() == null ? Collections.emptySet() : course.getUsers()
                        .stream()
                        .map(u -> userControllerMapper.mapUserToUserResponse(u, FOUND_USER_ON_COURSE))
                        .collect(Collectors.toSet())
        );
    }
}
