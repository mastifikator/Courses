package com.mts.teta.courses.controller.UI.admin;

import com.mts.teta.courses.domain.UserPrincipal;
import com.mts.teta.courses.dto.UI.UserRequestToAssign;
import com.mts.teta.courses.dto.UserRequestToCreate;
import com.mts.teta.courses.dto.UserRequestToUpdate;
import com.mts.teta.courses.service.CourseLister;
import com.mts.teta.courses.service.RoleLister;
import com.mts.teta.courses.service.StatisticsCounter;
import com.mts.teta.courses.service.UserLister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@Controller
@RequestMapping("/admin")
public class UserAdminController {

    @Autowired
    private StatisticsCounter statisticsCounter;

    @Autowired
    private CourseLister courseLister;

    @Autowired
    private UserLister userLister;

    @Autowired
    private RoleLister roleLister;

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

    @GetMapping("/users/edit/{userId}")
    public String userEdit(@PathVariable Long userId, Model model) {
        UserPrincipal user = userLister.userById(userId);
        statisticsCounter.countHandlerCall("admin edit user " + userId);

        model.addAttribute("title", "Панель администратора, Изменение пользователя");
        model.addAttribute("userRequestToUpdate", new UserRequestToUpdate());
        model.addAttribute("userRequestToAssign", new UserRequestToAssign());
        model.addAttribute("user", userLister.userById(userId));
        model.addAttribute("allroles", roleLister.getAllRoles());
        model.addAttribute("allcourses", courseLister.getAllCourses());

        if (userLister.isStudent(user)) {
            model.addAttribute("courses", courseLister.getAllCoursesForUser(user));
            model.addAttribute("isStudent", "true");
        } else {
            model.addAttribute("courses", Collections.emptySet());
            model.addAttribute("isStudent", "false");
        }

        return "/admin/editUser.html";
    }

    @PostMapping("/users/edit/{userId}")
    public String userEditForm(@ModelAttribute UserRequestToUpdate userRequestToUpdate,
                               @PathVariable Long userId) {
        userLister.updateUser(userId, userRequestToUpdate);

        return "redirect:/admin/users";
    }

    @PostMapping("/users/delete/{userId}")
    public String userDeleteForm(@PathVariable Long userId) {
        userLister.deleteUser(userId);

        return "redirect:/admin/users";
    }

    @PostMapping("/users/{userId}/courses/assign")
    public String userAssignToCourse(@ModelAttribute UserRequestToAssign userRequestToAssign,
                                     @PathVariable Long userId) {

        courseLister.assignedUserToCourse(userRequestToAssign.getCourseId(), userId);

        return "redirect:/admin/users/edit/" + userId;
    }

    @PostMapping("/users/{userId}/courses/{courseId}/unassigned")
    public String userUnassignedFromCourse(@PathVariable Long userId,
                                           @PathVariable Long courseId) {

        courseLister.unassignedUserToCourse(courseId, userId);

        return "redirect:/admin/users/edit/" + userId;
    }
}
