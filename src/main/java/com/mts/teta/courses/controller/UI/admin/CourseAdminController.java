package com.mts.teta.courses.controller.UI.admin;

import com.mts.teta.courses.dto.CourseRequestToCreate;
import com.mts.teta.courses.dto.CourseRequestToUpdate;
import com.mts.teta.courses.service.CourseLister;
import com.mts.teta.courses.service.ModuleLister;
import com.mts.teta.courses.service.StatisticsCounter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class CourseAdminController {

    @Autowired
    private StatisticsCounter statisticsCounter;

    @Autowired
    private CourseLister courseLister;

    @Autowired
    private ModuleLister moduleLister;

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
}
