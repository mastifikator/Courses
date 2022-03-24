package com.mts.teta.courses.controller;

import com.mts.teta.courses.domain.Course;
import com.mts.teta.courses.dto.CourseRequestToCreate;
import com.mts.teta.courses.dto.CourseRequestToUpdate;
import com.mts.teta.courses.dto.CourseResponse;
import com.mts.teta.courses.mapper.CourseControllerMapper;
import com.mts.teta.courses.service.CourseLister;
import com.mts.teta.courses.service.StatisticsCounter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/course")
public class CourseController {
    private final static String UPDATE_ANSWER = "Course Updated";
    private final static String CREATE_ANSWER = "Course Created";
    private final static String FILTER_ANSWER = "Course Filtered";
    private final static String DELETE_ANSWER = "Course Deleted";

    @Autowired
    private CourseLister courseLister;
    @Autowired
    private StatisticsCounter statisticsCounter;
    @Autowired
    private CourseControllerMapper courseControllerMapper;

    @GetMapping("/{id}")
    public Course getCourse(@PathVariable("id") Long id) {
        statisticsCounter.countHandlerCall("getCourse " + id);
        return courseLister.courseById(id);
    }

    @GetMapping("/filtered")
    public List<CourseResponse> getCoursesByTitlePrefix(@RequestParam(value = "titlePrefix", required = false) String titlePrefix) {
        statisticsCounter.countHandlerCall(FILTER_ANSWER + titlePrefix);

        return courseLister
                .coursesByTitlePrefix(titlePrefix)
                .stream()
                .map(c -> courseControllerMapper.mapCourseToCourseResponse(c, FILTER_ANSWER))
                .collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    public CourseResponse updateCourse(@PathVariable Long id,
                                       @Valid @RequestBody CourseRequestToUpdate request) {
        statisticsCounter.countHandlerCall(UPDATE_ANSWER + id + " " + request);

        return courseControllerMapper
                .mapCourseToCourseResponse(courseLister
                        .updateCourse(id, request), UPDATE_ANSWER);
    }

    @PostMapping
    public CourseResponse createCourse(@Valid @RequestBody CourseRequestToCreate request) {
        statisticsCounter.countHandlerCall(CREATE_ANSWER + request);

        return courseControllerMapper
                .mapCourseToCourseResponse(courseLister
                        .createCourse(request), CREATE_ANSWER);
    }

    @DeleteMapping("/{id}")
    public void deleteCourse(@PathVariable Long id) {
        statisticsCounter.countHandlerCall(DELETE_ANSWER + id);
        courseLister.deleteCourse(id);
    }

}
