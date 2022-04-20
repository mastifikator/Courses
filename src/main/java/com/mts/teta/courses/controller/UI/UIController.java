package com.mts.teta.courses.controller.UI;

import com.mts.teta.courses.domain.Course;
import com.mts.teta.courses.domain.Lesson;
import com.mts.teta.courses.domain.UserPrincipal;
import com.mts.teta.courses.mapper.CourseControllerMapper;
import com.mts.teta.courses.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.stream.Collectors;

@Controller
public class UIController {

    private final static String GET_ANSWER = "Course found";

    @Autowired
    private CourseLister courseLister;

    @Autowired
    private LessonLister lessonLister;

    @Autowired
    private StatisticsCounter statisticsCounter;

    @Autowired
    private CourseControllerMapper courseControllerMapper;

    @GetMapping({"/", "/index"})
    public String index(Model model) {
        statisticsCounter.countHandlerCall("getCourses");

        model.addAttribute("title", "Добро пожаловать на образовательную платформу!");
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

        model.addAttribute("title", "Результаты поиска по запросу " + titlePrefix);
        model.addAttribute("courses", courseLister.coursesByTitlePrefix(titlePrefix)
                .stream()
                .map(c -> courseControllerMapper.mapCourseToCourseResponse(c, GET_ANSWER))
                .collect(Collectors.toList()));

        return "search.html";
    }

    @GetMapping("/learn/{courseId}")
    public String learnCourse(@PathVariable("courseId") Long courseId, Model model) {
        statisticsCounter.countHandlerCall("learnCourse " + courseId);

        Course course = courseLister.courseById(courseId);
        model.addAttribute("title", "Добро пожаловать на курс " +
                course.getTitle() + " от " +
                course.getAuthor());

        model.addAttribute("course", course);
        model.addAttribute("modules", courseLister.getModulesFromCourse(courseId));

        return "learnCourse.html";
    }

    @GetMapping("/learn/{courseId}/lesson/{lessonId}")
    public String learnLesson(@PathVariable("courseId") Long courseId,
                              @PathVariable("lessonId") Long lessonId,
                              Model model) {
        statisticsCounter.countHandlerCall("learnLesson " + lessonId);

        Course course = courseLister.courseById(courseId);
        Lesson lesson = lessonLister.lessonById(lessonId);
        model.addAttribute("title", "Курс " + course.getTitle() + ", урок " + lesson.getTitle());

        model.addAttribute("course", courseLister.courseById(courseId));
        model.addAttribute("modules", courseLister.getModulesFromCourse(courseId));

        model.addAttribute("lesson", lesson);

        return "learnLesson.html";
    }

}
