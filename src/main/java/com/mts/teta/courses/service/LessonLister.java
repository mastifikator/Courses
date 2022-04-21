package com.mts.teta.courses.service;

import com.mts.teta.courses.dao.LessonRepository;
import com.mts.teta.courses.domain.Lesson;
import com.mts.teta.courses.domain.Module;
import com.mts.teta.courses.dto.LessonRequestToCreate;
import com.mts.teta.courses.dto.LessonRequestToUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;

@Component
public class LessonLister {

    @Autowired
    private ModuleLister moduleLister;

    @Autowired
    private LessonRepository lessonRepository;

    public Lesson createLesson(LessonRequestToCreate request) {
        Module module = moduleLister.moduleById(request.getModuleId());
        Lesson lesson = new Lesson(request.getTitle(),
                request.getText(),
                request.getAuthor(),
                module);

        lessonRepository.save(lesson);
        return lesson;
    }

    public Lesson updateLesson(Long lessonId, LessonRequestToUpdate request) {
        Lesson lesson = lessonRepository.getById(lessonId);
        lesson.setTitle(request.getTitle());
        lesson.setText(request.getText());
        lesson.setAuthor(request.getAuthor());
        lesson.setDateChanged(new Timestamp(System.currentTimeMillis()));

        lessonRepository.save(lesson);
        return lesson;
    }

    public Lesson lessonById(Long lessonId) {
        return lessonRepository.getById(lessonId);
    }

    public void deleteLesson(Long lessonId) {
        lessonRepository.deleteById(lessonId);
    }

    public List<Lesson> lessonsByModuleId(Long moduleId) {
        return lessonRepository.findAllByModule_ModuleId(moduleId);
    }
}
