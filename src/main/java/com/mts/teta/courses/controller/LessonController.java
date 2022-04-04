package com.mts.teta.courses.controller;

import com.mts.teta.courses.dto.LessonRequestToCreate;
import com.mts.teta.courses.dto.LessonRequestToUpdate;
import com.mts.teta.courses.dto.LessonResponse;
import com.mts.teta.courses.mapper.LessonControllerMapper;
import com.mts.teta.courses.service.LessonLister;
import com.mts.teta.courses.service.StatisticsCounter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/lesson")
public class LessonController {
    private final static String GET_ANSWER = "Lesson found";
    private final static String UPDATE_ANSWER = "Lesson Updated";
    private final static String CREATE_ANSWER = "Lesson Created";
    private final static String DELETE_ANSWER = "Lesson Deleted";

    @Autowired
    private LessonLister lessonLister;
    @Autowired
    private StatisticsCounter statisticsCounter;
    @Autowired
    private LessonControllerMapper lessonControllerMapper;

    @GetMapping("/{lessonId}")
    public LessonResponse getLesson(@PathVariable("lessonId") Long lessonId) {
        statisticsCounter.countHandlerCall("getLesson " + lessonId);
        return lessonControllerMapper.mapLessonToLessonResponse(lessonLister.lessonById(lessonId), GET_ANSWER);
    }

    @PostMapping
    public LessonResponse createLesson(@Valid @RequestBody LessonRequestToCreate request) {
        statisticsCounter.countHandlerCall(CREATE_ANSWER + request);

        return lessonControllerMapper
                .mapLessonToLessonResponse(lessonLister
                        .createLesson(request), CREATE_ANSWER);
    }

    @PutMapping("/{lessonId}")
    public LessonResponse updateLesson(@PathVariable Long lessonId,
                                       @Valid @RequestBody LessonRequestToUpdate request) {
        statisticsCounter.countHandlerCall(UPDATE_ANSWER + lessonId + " " + request);

        return lessonControllerMapper
                .mapLessonToLessonResponse(lessonLister
                        .updateLesson(lessonId, request), UPDATE_ANSWER);
    }

    @DeleteMapping("/{lessonId}")
    public void deleteLesson(@PathVariable Long lessonId) {
        statisticsCounter.countHandlerCall(DELETE_ANSWER + lessonId);
        lessonLister.deleteLesson(lessonId);
    }

}
