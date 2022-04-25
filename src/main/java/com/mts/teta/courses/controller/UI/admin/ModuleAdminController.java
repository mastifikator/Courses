package com.mts.teta.courses.controller.UI.admin;

import com.mts.teta.courses.domain.Course;
import com.mts.teta.courses.domain.Module;
import com.mts.teta.courses.dto.ModuleRequestToCreate;
import com.mts.teta.courses.dto.ModuleRequestToUpdate;
import com.mts.teta.courses.service.CourseLister;
import com.mts.teta.courses.service.LessonLister;
import com.mts.teta.courses.service.ModuleLister;
import com.mts.teta.courses.service.StatisticsCounter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class ModuleAdminController {

    @Autowired
    private StatisticsCounter statisticsCounter;

    @Autowired
    private CourseLister courseLister;

    @Autowired
    private ModuleLister moduleLister;

    @Autowired
    private LessonLister lessonLister;

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
}
