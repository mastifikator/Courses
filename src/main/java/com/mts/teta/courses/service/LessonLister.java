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

    public Lesson updateLesson(Long lessonId, LessonRequestToUpdate request) {
        Lesson lesson = lessonRepository.getById(lessonId);
        lesson.setTitle(request.getTitle());
        lesson.setText(request.getText());
        lessonRepository.save(lesson);
        return lesson;
    }

    public Lesson lessonById(Long lessonId) {
        return lessonRepository.getById(lessonId);
    }

    public void deleteLesson(Long lessonId) {
        lessonRepository.deleteById(lessonId);
    }

    public List<Lesson> lessonsByCourseId(Long courseId) {
        return lessonRepository.findAllLessonsForCourse(courseId);
    }
}
