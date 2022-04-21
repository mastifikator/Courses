package com.mts.teta.courses.controller.UI;

import com.mts.teta.courses.domain.Role;
import com.mts.teta.courses.dto.UserRequestToCreate;
import com.mts.teta.courses.mapper.admin.CourseAdminMapper;
import com.mts.teta.courses.service.CourseLister;
import com.mts.teta.courses.service.RoleLister;
import com.mts.teta.courses.service.StatisticsCounter;
import com.mts.teta.courses.service.UserLister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    private RoleLister roleLister;

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

    //User

    @GetMapping("/users/create")
    public String userCreate(Model model) {
        statisticsCounter.countHandlerCall("admin create user");

        model.addAttribute("title", "Панель администратора, Создание пользователя");
        model.addAttribute("userRequestToCreate", new UserRequestToCreate());
        model.addAttribute("allroles", roleLister.getAllRoles());

        return "/admin/createUser.html";
    }

    @PostMapping("/users/create")
    public String userCreateForm(@ModelAttribute UserRequestToCreate userRequestToCreate) {
        userLister.createUser(userRequestToCreate);

        return "redirect:/admin/users";
    }

    @PostMapping("/users/delete/{userId}")
    public String userDeleteForm(@PathVariable Long userId) {
        userLister.deleteUser(userId);

        return "redirect:/admin/users";
    }

    //Course
}
