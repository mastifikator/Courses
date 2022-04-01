package com.mts.teta.courses.service;

import com.mts.teta.courses.dao.LessonRepository;
import com.mts.teta.courses.domain.Course;
import com.mts.teta.courses.domain.Lesson;
import com.mts.teta.courses.dto.LessonRequestToCreate;
import com.mts.teta.courses.dto.LessonRequestToUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LessonLister {

    @Autowired
    private CourseLister courseLister;

    @Autowired
    private LessonRepository lessonRepository;

    public Lesson createLesson(LessonRequestToCreate request) {
        Course course = courseLister.courseById(request.getCourseId());
        Lesson lesson = new Lesson(request.getTitle(), request.getText(), course);
        lessonRepository.save(lesson);
        return lesson;
    }

    public Lesson updateLesson(Long id, LessonRequestToUpdate request) {
        Lesson lesson = lessonRepository.getById(id);
        lesson.setTitle(request.getTitle());
        lesson.setText(request.getText());
        lessonRepository.save(lesson);
        return lesson;
    }

    public Lesson lessonById(Long id) {
        return lessonRepository.getById(id);
    }

    public void deleteLesson(Long id) {
        lessonRepository.deleteById(id);
    }

    public List<Lesson> lessonsByCourseId(Long id) {
        return lessonRepository.findAllLessonsForCourse(id);
    }
}
