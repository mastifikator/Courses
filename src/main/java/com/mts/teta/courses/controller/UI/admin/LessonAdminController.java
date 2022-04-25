package com.mts.teta.courses.controller.UI.admin;

import com.mts.teta.courses.domain.Course;
import com.mts.teta.courses.domain.Lesson;
import com.mts.teta.courses.domain.Module;
import com.mts.teta.courses.dto.LessonRequestToCreate;
import com.mts.teta.courses.dto.LessonRequestToUpdate;
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
public class LessonAdminController {

    @Autowired
    private StatisticsCounter statisticsCounter;

    @Autowired
    private CourseLister courseLister;

    @Autowired
    private ModuleLister moduleLister;

    @Autowired
    private LessonLister lessonLister;

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
