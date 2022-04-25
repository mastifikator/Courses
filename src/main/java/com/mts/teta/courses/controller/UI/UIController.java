package com.mts.teta.courses.controller.UI;

import com.mts.teta.courses.domain.Course;
import com.mts.teta.courses.domain.Lesson;
import com.mts.teta.courses.domain.UserPrincipal;
import com.mts.teta.courses.dto.UI.UserRequestToAssign;
import com.mts.teta.courses.mapper.CourseControllerMapper;
import com.mts.teta.courses.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    private UserLister userLister;

    @Autowired
    private StatisticsCounter statisticsCounter;

    @Autowired
    private CourseControllerMapper courseControllerMapper;

    @GetMapping({"/", "/index"})
    public String index(Model model, Authentication auth) {
        UserPrincipal user = userLister.userByUsername(auth.getName());
        statisticsCounter.countHandlerCall("getCourses for " + user.getUserId());

        model.addAttribute("title", "Добро пожаловать на образовательную платформу!");
        model.addAttribute("courses", courseLister
                .getAllCoursesForUser(user)
                .stream()
                .map(c -> courseControllerMapper.mapCourseToCourseResponse(c, GET_ANSWER))
                .collect(Collectors.toList()));

        return "index.html";
    }

    @GetMapping("/search")
    public String search(@RequestParam(value = "titlePrefix", required = false) String titlePrefix,
                         Model model,
                         Authentication auth) {
        UserPrincipal user = userLister.userByUsername(auth.getName());
        statisticsCounter.countHandlerCall("searchCourses for " + user.getUserId());

        model.addAttribute("title", "Результаты поиска по запросу " + titlePrefix);
        model.addAttribute("courses", courseLister.coursesByTitlePrefixForUser(user, titlePrefix)
                .stream()
                .map(c -> courseControllerMapper.mapCourseToCourseResponse(c, GET_ANSWER))
                .collect(Collectors.toList()));

        return "search.html";
    }

    @GetMapping("/learn/{courseId}")
    public String learnCourse(@PathVariable("courseId") Long courseId,
                              Model model,
                              Authentication auth) {
        UserPrincipal user = userLister.userByUsername(auth.getName());
        Course course = courseLister.courseById(courseId);
        statisticsCounter.countHandlerCall("User " + user.getUserId() + " learnCourse " + courseId);

        if (userLister.isStudent(user) && !user.getCourses().contains(course)) {
            throw new AccessDeniedException("Вам закрыт доступ к данному курсу, обратитесь к преподавателю");
        }

        model.addAttribute("title", user.getNickname() + ", добро пожаловать на курс " +
                course.getTitle() + " от " +
                course.getAuthor());
        model.addAttribute("course", course);
        model.addAttribute("modules", courseLister.getModulesFromCourse(courseId));

        return "learnCourse.html";
    }

    @GetMapping("/learn/{courseId}/lesson/{lessonId}")
    public String learnLesson(@PathVariable("courseId") Long courseId,
                              @PathVariable("lessonId") Long lessonId,
                              Model model,
                              Authentication auth) {
        UserPrincipal user = userLister.userByUsername(auth.getName());
        Course course = courseLister.courseById(courseId);
        Lesson lesson = lessonLister.lessonById(lessonId);
        statisticsCounter.countHandlerCall(user.getUserId() + " learnLesson " + lessonId);

        if (userLister.isStudent(user) && !user.getCourses().contains(course)) {
            throw new AccessDeniedException("Вам закрыт доступ к данному уроку, обратитесь к преподавателю");
        }

        model.addAttribute("title", "Курс " + course.getTitle() + ", урок " + lesson.getTitle());
        model.addAttribute("course", courseLister.courseById(courseId));
        model.addAttribute("modules", courseLister.getModulesFromCourse(courseId));
        model.addAttribute("lesson", lesson);
        model.addAttribute("user", user);

        if (lessonLister.isPassed(lessonId, user.getUserId())) {
            model.addAttribute("isPassed", "true");
        } else {
            model.addAttribute("isPassed", "false");
        }

        return "learnLesson.html";
    }

    @PostMapping("/learn/{courseId}/lesson/{lessonId}/passed/{userId}")
    public String passedLesson(@PathVariable Long courseId,
                               @PathVariable Long lessonId,
                               @PathVariable Long userId) {
        statisticsCounter.countHandlerCall(userId + " passed " + lessonId);
        lessonLister.passedLesson(lessonId, userId);

        return "redirect:/learn/" + courseId + "/lesson/" + lessonId;
    }

    @PostMapping("/learn/{courseId}/lesson/{lessonId}/unpassed/{userId}")
    public String unpassedLesson(@PathVariable Long courseId,
                                 @PathVariable Long lessonId,
                                 @PathVariable Long userId) {
        statisticsCounter.countHandlerCall(userId + " unpassed " + lessonId);
        lessonLister.unpassedLesson(lessonId, userId);

        return "redirect:/learn/" + courseId + "/lesson/" + lessonId;
    }

    @GetMapping({"/accessDenied"})
    public String accessDenied(Model model, Authentication auth) {
        UserPrincipal user = userLister.userByUsername(auth.getName());
        statisticsCounter.countHandlerCall("access denied for " + user.getUserId());

        model.addAttribute("title", "Доступ запрещен!");

        return "error.html";
    }

}
