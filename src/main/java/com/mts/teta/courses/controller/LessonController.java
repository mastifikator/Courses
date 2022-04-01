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
import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping("/{id}")
    public LessonResponse getLesson(@PathVariable("id") Long id) {
        statisticsCounter.countHandlerCall("getLesson " + id);
        return lessonControllerMapper.mapLessonToLessonResponse(lessonLister.lessonById(id), GET_ANSWER);
    }

    @GetMapping("/course/{id}")
    public List<LessonResponse> getLessonsForCourse(@PathVariable("id") Long id) {
        statisticsCounter.countHandlerCall("getLessons for course " + id);

        return lessonLister.lessonsByCourseId(id)
                .stream()
                .map(l -> lessonControllerMapper.mapLessonToLessonResponse(l, GET_ANSWER))
                .collect(Collectors.toList());
    }

    @PostMapping
    public LessonResponse createLesson(@Valid @RequestBody LessonRequestToCreate request) {
        statisticsCounter.countHandlerCall(CREATE_ANSWER + request);

        return lessonControllerMapper
                .mapLessonToLessonResponse(lessonLister
                        .createLesson(request), CREATE_ANSWER);
    }

    @PutMapping("/{id}")
    public LessonResponse updateLesson(@PathVariable Long id,
                                       @Valid @RequestBody LessonRequestToUpdate request) {
        statisticsCounter.countHandlerCall(UPDATE_ANSWER + id + " " + request);

        return lessonControllerMapper
                .mapLessonToLessonResponse(lessonLister
                        .updateLesson(id, request), UPDATE_ANSWER);
    }

    @DeleteMapping("/{id}")
    public void deleteLesson(@PathVariable Long id) {
        statisticsCounter.countHandlerCall(DELETE_ANSWER + id);
        lessonLister.deleteLesson(id);
    }

}
