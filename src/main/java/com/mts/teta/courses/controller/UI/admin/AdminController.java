package com.mts.teta.courses.controller.UI.admin;

import com.mts.teta.courses.mapper.admin.CourseAdminMapper;
import com.mts.teta.courses.service.CourseLister;
import com.mts.teta.courses.service.StatisticsCounter;
import com.mts.teta.courses.service.UserLister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final static String GET_ANSWER = "Course found";

    @Autowired
    private StatisticsCounter statisticsCounter;

    @Autowired
    private CourseLister courseLister;

    @Autowired
    private CourseAdminMapper courseAdminMapper;

    @Autowired
    private UserLister userLister;

    @GetMapping
    public String admin(Model model) {
        statisticsCounter.countHandlerCall("admin");

        model.addAttribute("title", "Панель администратора");

        return "/admin/admin.html";
    }

    @GetMapping("/courses")
    public String courses(Model model) {
        statisticsCounter.countHandlerCall("admin courses");

        model.addAttribute("title", "Панель администратора, Курсы");
        model.addAttribute("courses", courseLister
                .getAllCourses()
                .stream()
                .map(c -> courseAdminMapper.mapCourseToCourseResponse(c, GET_ANSWER))
                .collect(Collectors.toList()));

        return "/admin/courses.html";
    }

    @GetMapping("/users")
    public String users(Model model) {
        statisticsCounter.countHandlerCall("admin users");

        model.addAttribute("title", "Панель администратора, Пользователи");
        model.addAttribute("users", userLister.getAllUsers());

        return "/admin/users.html";
    }

}
