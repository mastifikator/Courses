package com.mts.teta.courses.controller;

import com.mts.teta.courses.domain.Course;
import com.mts.teta.courses.dto.CourseRequestToCreate;
import com.mts.teta.courses.dto.CourseRequestToUpdate;
import com.mts.teta.courses.service.CourseLister;
import com.mts.teta.courses.service.StatisticsCounter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseLister courseLister;
    @Autowired
    private StatisticsCounter statisticsCounter;

    @GetMapping("/{id}")
    public Course getCourse(@PathVariable("id") Long id) {
        statisticsCounter.countHandlerCall("getCourse " + id);
        return courseLister.courseById(id);
    }

    @GetMapping("/filtered")
    public List<Course> getCoursesByTitlePrefix(@RequestParam(value = "titlePrefix", required = false) String titlePrefix) {
        statisticsCounter.countHandlerCall("getCoursesByTitlePrefix " + titlePrefix);
        return courseLister.coursesByTitlePrefix(titlePrefix);
    }

    @PutMapping("/{id}")
    public void updateCourse(@PathVariable Long id,
                             @Valid @RequestBody CourseRequestToUpdate request) {
        statisticsCounter.countHandlerCall("updateCourse " + id + " " + request);
        courseLister.updateCourse(id, request);
    }

    @PostMapping
    public Course createCourse(@Valid @RequestBody CourseRequestToCreate request) {
        statisticsCounter.countHandlerCall("createCourse " + request);
        return courseLister.createCourse(request);
    }

    @DeleteMapping("/{id}")
    public void deleteCourse(@PathVariable Long id) {
        statisticsCounter.countHandlerCall("deleteCourse " + id);
        courseLister.deleteCourse(id);
    }

}
