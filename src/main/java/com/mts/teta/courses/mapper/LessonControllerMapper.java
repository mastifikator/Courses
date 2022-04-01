package com.mts.teta.courses.mapper;

import com.mts.teta.courses.domain.Lesson;
import com.mts.teta.courses.dto.LessonResponse;
import org.springframework.stereotype.Component;

@Component
public class LessonControllerMapper {

    public LessonResponse mapLessonToLessonResponse(Lesson lesson, String actionResponse) {
        return new LessonResponse(lesson.getLesson_id(),
                lesson.getTitle(),
                lesson.getText(),
                lesson.getCourse().getCourse_id(),
                actionResponse);
    }
}
