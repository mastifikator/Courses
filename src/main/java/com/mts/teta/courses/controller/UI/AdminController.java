package com.mts.teta.courses.controller.UI;

import com.mts.teta.courses.domain.Course;
import com.mts.teta.courses.domain.Lesson;
import com.mts.teta.courses.domain.Module;
import com.mts.teta.courses.domain.UserPrincipal;
import com.mts.teta.courses.dto.*;
import com.mts.teta.courses.dto.UI.UserRequestToAssign;
import com.mts.teta.courses.dto.UI.UserRequestToChange;
import com.mts.teta.courses.mapper.admin.CourseAdminMapper;
import com.mts.teta.courses.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
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
    private ModuleLister moduleLister;

    @Autowired
    private LessonLister lessonLister;

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

    //Course

    @GetMapping("/courses/create")
    public String courseCreate(Model model) {
        statisticsCounter.countHandlerCall("admin create course");

        model.addAttribute("title", "Панель администратора, Создание курса");
        model.addAttribute("courseRequestToCreate", new CourseRequestToCreate());

        return "/admin/createCourse.html";
    }

    @PostMapping("/courses/create")
    public String courseCreateForm(@ModelAttribute CourseRequestToCreate courseRequestToCreate) {
        courseLister.createCourse(courseRequestToCreate);

        return "redirect:/admin/courses";
    }

    @GetMapping("/courses/edit/{courseId}")
    public String courseEdit(@PathVariable Long courseId, Model model) {
        statisticsCounter.countHandlerCall("admin edit course");

        model.addAttribute("title", "Панель администратора, Изменение курса");
        model.addAttribute("courseRequestToUpdate", new CourseRequestToUpdate());
        model.addAttribute("course", courseLister.courseById(courseId));
        model.addAttribute("modules", moduleLister.modulesByCourseId(courseId));

        return "/admin/editCourse.html";
    }

    @PostMapping("/courses/edit/{courseId}")
    public String courseEditForm(@ModelAttribute CourseRequestToUpdate courseRequestToUpdate,
                                 @PathVariable Long courseId) {
        courseLister.updateCourse(courseId, courseRequestToUpdate);

        return "redirect:/admin/courses";
    }

    @PostMapping("/courses/delete/{courseId}")
    public String courseDeleteForm(@PathVariable Long courseId) {
        courseLister.deleteCourse(courseId);

        return "redirect:/admin/courses";
    }

    //Module

    @GetMapping("/modules/create/{courseId}")
    public String moduleCreate(@PathVariable Long courseId,
                               Model model) {
        Course course = courseLister.courseById(courseId);
        statisticsCounter.countHandlerCall("admin create module");

        model.addAttribute("title", "Панель администратора, Создание модуля для курса " + course.getTitle());
        model.addAttribute("moduleRequestToCreate", new ModuleRequestToCreate());
        model.addAttribute("course", course);

        return "/admin/createModule.html";
    }

    @PostMapping("/modules/create/{courseId}")
    public String moduleCreateForm(@ModelAttribute ModuleRequestToCreate moduleRequestToCreate,
                                   @PathVariable Long courseId) {
        moduleLister.createModule(moduleRequestToCreate);

        return "redirect:/admin/courses/edit/" + courseId;
    }

    @GetMapping("/courses/{courseId}/modules/edit/{moduleId}")
    public String moduleEdit(@PathVariable Long courseId,
                             @PathVariable Long moduleId,
                             Model model) {
        Course course = courseLister.courseById(courseId);
        Module module = moduleLister.moduleById(moduleId);
        statisticsCounter.countHandlerCall("admin edit module");

        model.addAttribute("title", "Панель администратора, Изменение модуля " +
                module.getTitle() + ", курса " +
                course.getTitle());

        model.addAttribute("moduleRequestToUpdate", new ModuleRequestToUpdate());
        model.addAttribute("course", course);
        model.addAttribute("module", module);
        model.addAttribute("lessons", lessonLister.lessonsByModuleId(moduleId));

        return "/admin/editModule.html";
    }

    @PostMapping("/courses/{courseId}/modules/edit/{moduleId}")
    public String moduleEditForm(@ModelAttribute ModuleRequestToUpdate moduleRequestToUpdate,
                                 @PathVariable Long courseId,
                                 @PathVariable Long moduleId) {
        moduleLister.updateModule(moduleId, moduleRequestToUpdate);

        return "redirect:/admin/courses/edit/" + courseId;
    }

    @PostMapping("/courses/{courseId}/modules/delete/{moduleId}")
    public String moduleDeleteForm(@PathVariable Long courseId,
                                   @PathVariable Long moduleId) {
        moduleLister.deleteModule(moduleId);

        return "redirect:/admin/courses/edit/" + courseId;
    }

    //Lesson
    @GetMapping("/courses/{courseId}/modules/{moduleId}/lessons/create")
    public String lessonCreate(@PathVariable Long courseId,
                               @PathVariable Long moduleId,
                               Model model) {
        Course course = courseLister.courseById(courseId);
        Module module = moduleLister.moduleById(moduleId);
        statisticsCounter.countHandlerCall("admin create lesson");

        model.addAttribute("title", "Панель администратора, Создание Урока для модуля " + module.getTitle());
        model.addAttribute("lessonRequestToCreate", new LessonRequestToCreate());
        model.addAttribute("course", course);
        model.addAttribute("module", module);

        return "/admin/createLesson.html";
    }

    @PostMapping("/courses/{courseId}/modules/{moduleId}/lessons/create")
    public String lessonCreateForm(@ModelAttribute LessonRequestToCreate lessonRequestToCreate,
                                   @PathVariable Long courseId,
                                   @PathVariable Long moduleId) {
        lessonLister.createLesson(lessonRequestToCreate);

        return "redirect:/admin/courses/" + courseId + "/modules/edit/" + moduleId;
    }

    @GetMapping("/courses/{courseId}/modules/{moduleId}/lessons/edit/{lessonId}")
    public String lessonEdit(@PathVariable Long courseId,
                             @PathVariable Long moduleId,
                             @PathVariable Long lessonId,
                             Model model) {
        Course course = courseLister.courseById(courseId);
        Module module = moduleLister.moduleById(moduleId);
        Lesson lesson = lessonLister.lessonById(lessonId);
        statisticsCounter.countHandlerCall("admin edit lesson");

        model.addAttribute("title", "Панель администратора, Изменение урока " +
                course.getTitle() + ", модуля " +
                module.getTitle());

        model.addAttribute("lessonRequestToUpdate", new LessonRequestToUpdate());
        model.addAttribute("course", course);
        model.addAttribute("module", module);
        model.addAttribute("lesson", lesson);

        return "/admin/editLesson.html";
    }

    @PostMapping("/courses/{courseId}/modules/{moduleId}/lessons/edit/{lessonId}")
    public String lessonEditForm(@ModelAttribute LessonRequestToUpdate lessonRequestToUpdate,
                                 @PathVariable Long courseId,
                                 @PathVariable Long moduleId,
                                 @PathVariable Long lessonId) {
        lessonLister.updateLesson(lessonId, lessonRequestToUpdate);

        return "redirect:/admin/courses/" + courseId + "/modules/edit/" + moduleId;
    }

    @PostMapping("/courses/{courseId}/modules/{moduleId}/lessons/delete/{lessonId}")
    public String lessonDeleteForm(@PathVariable Long courseId,
                                   @PathVariable Long moduleId,
                                   @PathVariable Long lessonId) {
        lessonLister.deleteLesson(lessonId);

        return "redirect:/admin/courses/" + courseId + "/modules/edit/" + moduleId;
    }


}
