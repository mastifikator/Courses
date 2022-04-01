package com.mts.teta.courses.mapper;

import com.mts.teta.courses.domain.Course;
import com.mts.teta.courses.dto.CourseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CourseControllerMapper {
    private static final String FOUND_USER_ON_COURSE = "Found user assign to Course";

    @Autowired
    UserControllerMapper userControllerMapper;

    public CourseResponse mapCourseToCourseResponse(Course course, String actionDescription) {
        return new CourseResponse(course.getCourse_id(),
                course.getAuthor(),
                course.getTitle(),
                actionDescription,
                course.getUsers()
                        .stream()
                        .map(u -> userControllerMapper.mapUserToUserResponse(u, FOUND_USER_ON_COURSE))
                        .collect(Collectors.toSet())
        );
    }
}
