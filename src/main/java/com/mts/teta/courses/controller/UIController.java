package com.mts.teta.courses.controller;

import com.mts.teta.courses.mapper.CourseControllerMapper;
import com.mts.teta.courses.service.CourseLister;
import com.mts.teta.courses.service.StatisticsCounter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.stream.Collectors;

@Controller
public class UIController {

    private final static String GET_ANSWER = "Course found";

    @Autowired
    private CourseLister courseLister;

    @Autowired
    private StatisticsCounter statisticsCounter;

    @Autowired
    private CourseControllerMapper courseControllerMapper;

    @GetMapping({"/", "/index"})
    public String index(Model model) {
        statisticsCounter.countHandlerCall("getCourses");

        model.addAttribute("title", "Courses");
        model.addAttribute("courses", courseLister
                .getAllCourses()
                .stream()
                .map(c -> courseControllerMapper.mapCourseToCourseResponse(c, GET_ANSWER))
                .collect(Collectors.toList()));

        return "index.html";
    }

    @GetMapping("/learn/{courseId}")
    public String courses(@PathVariable("courseId") Long courseId, Model model) {
        statisticsCounter.countHandlerCall("getCourses");

        model.addAttribute("title", "Courses");
        model.addAttribute("theme", courseLister
                .getAllCourses()
                .stream()
                .map(c -> courseControllerMapper.mapCourseToCourseResponse(c, GET_ANSWER))
                .collect(Collectors.toList()));

        return "index.html";
    }
}
