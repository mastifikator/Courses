package com.mts.teta.courses.mapper;

import com.mts.teta.courses.domain.Course;
import com.mts.teta.courses.dto.CourseResponse;
import org.springframework.stereotype.Component;

@Component
public class CourseControllerMapper {

    public CourseResponse mapCourseToCourseResponse(Course course, String actionDescription) {
        return new CourseResponse(course.getId(),
                course.getAuthor(),
                course.getTitle(),
                actionDescription);
    }
}
