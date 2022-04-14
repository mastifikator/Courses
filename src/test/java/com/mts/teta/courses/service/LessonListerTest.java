package com.mts.teta.courses.service;

import com.mts.teta.courses.domain.Lesson;
import com.mts.teta.courses.dto.LessonRequestToCreate;
import com.mts.teta.courses.dto.LessonRequestToUpdate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LessonListerTest extends PreparedDatabase {

    @Autowired
    protected LessonLister lessonLister;

    @Test
    @Transactional
    void createLesson() {
        LessonRequestToCreate lessonRequestToCreate = new LessonRequestToCreate();
        lessonRequestToCreate.setTitle("Title4");
        lessonRequestToCreate.setText("Text4");
        lessonRequestToCreate.setModuleId(3L);

        Lesson lesson = lessonLister.createLesson(lessonRequestToCreate);

        assertTrue(lessonRepository.findAll().contains(lesson));
    }

    @Test
    @Transactional
    void updateLesson() {
        LessonRequestToUpdate lessonRequestToUpdate = new LessonRequestToUpdate();
        lessonRequestToUpdate.setTitle("Title3_new");
        lessonRequestToUpdate.setText("Text3_new");

        Lesson lesson = lessonLister.updateLesson(3L, lessonRequestToUpdate);

        assertEquals(lessonLister.lessonById(3L).getText(), lesson.getText());
    }

    @Test
    @Transactional
    void lessonById() {
        Lesson lesson = lessonLister.lessonById(1L);

        assertEquals("Text1", lesson.getText());
    }

    @Test
    void deleteLesson() {
        LessonRequestToCreate lessonRequestToCreate = new LessonRequestToCreate();
        lessonRequestToCreate.setTitle("Title5");
        lessonRequestToCreate.setText("Text5");
        lessonRequestToCreate.setModuleId(3L);

        int countModuleBeforeCreate = lessonRepository.findAll().size();
        Lesson lesson = lessonLister.createLesson(lessonRequestToCreate);
        lessonLister.deleteLesson(lesson.getLessonId());

        assertEquals(countModuleBeforeCreate, lessonRepository.findAll().size());
    }

    @Test
    @Transactional
    void lessonsByModuleId() {
        List<Lesson> lessons = lessonLister.lessonsByModuleId(1L);

        assertEquals(1, lessons.size());
    }
}