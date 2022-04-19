package com.mts.teta.courses.controller;

import com.mts.teta.courses.mapper.CourseControllerMapper;
import com.mts.teta.courses.service.CourseLister;
import com.mts.teta.courses.service.StatisticsCounter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping("/search")
    public String search(@RequestParam(value = "titlePrefix", required = false) String titlePrefix, Model model) {
        statisticsCounter.countHandlerCall("searchCourses");

        model.addAttribute("title", "Search Result");
        model.addAttribute("courses", courseLister.coursesByTitlePrefix(titlePrefix)
                .stream()
                .map(c -> courseControllerMapper.mapCourseToCourseResponse(c, GET_ANSWER))
                .collect(Collectors.toList()));

        return "search.html";
    }

    @GetMapping("/learn/{courseId}")
    public String learn(@PathVariable("courseId") Long courseId, Model model) {
        statisticsCounter.countHandlerCall("learnCourses");

        model.addAttribute("course", courseLister.courseById(courseId));
        model.addAttribute("title", "Learn");

        return "learn.html";
    }
}
