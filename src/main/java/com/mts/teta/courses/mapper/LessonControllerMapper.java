package com.mts.teta.courses.mapper;

import com.mts.teta.courses.domain.Lesson;
import com.mts.teta.courses.dto.LessonResponse;
import org.springframework.stereotype.Component;

@Component
public class LessonControllerMapper {

    public LessonResponse mapLessonToLessonResponse(Lesson lesson, String actionResponse) {
        return new LessonResponse(lesson.getLessonId(),
                lesson.getTitle(),
                lesson.getText(),
                lesson.getAuthor(),
                lesson.getModule().getModuleId(),
                actionResponse);
    }
}
