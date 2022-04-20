package com.mts.teta.courses.controller;

import com.mts.teta.courses.dto.LessonResponse;
import com.mts.teta.courses.dto.ModuleRequestToCreate;
import com.mts.teta.courses.dto.ModuleRequestToUpdate;
import com.mts.teta.courses.dto.ModuleResponse;
import com.mts.teta.courses.mapper.LessonControllerMapper;
import com.mts.teta.courses.mapper.ModuleControllerMapper;
import com.mts.teta.courses.service.CourseLister;
import com.mts.teta.courses.service.LessonLister;
import com.mts.teta.courses.service.ModuleLister;
import com.mts.teta.courses.service.StatisticsCounter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/module")
public class ModuleController {
    private final static String GET_ANSWER = "Module found";
    private final static String UPDATE_ANSWER = "Module Updated";
    private final static String CREATE_ANSWER = "Module Created";
    private final static String DELETE_ANSWER = "Module Deleted";

    @Autowired
    private ModuleLister moduleLister;
    @Autowired
    private StatisticsCounter statisticsCounter;
    @Autowired
    private LessonLister lessonLister;
    @Autowired
    private ModuleControllerMapper moduleControllerMapper;
    @Autowired
    private LessonControllerMapper lessonControllerMapper;

    @GetMapping("/{moduleId}")
    public ModuleResponse getModule(@PathVariable("moduleId") Long moduleId) {
        statisticsCounter.countHandlerCall("getModule " + moduleId);
        return moduleControllerMapper.mapModuleToModuleResponse(moduleLister.moduleById(moduleId), GET_ANSWER);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping
    public ModuleResponse createModule(@Valid @RequestBody ModuleRequestToCreate request) {
        statisticsCounter.countHandlerCall(CREATE_ANSWER + request);

        return moduleControllerMapper
                .mapModuleToModuleResponse(moduleLister
                        .createModule(request), CREATE_ANSWER);
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("/{moduleId}")
    public ModuleResponse updateModule(@PathVariable Long moduleId,
                                       @Valid @RequestBody ModuleRequestToUpdate request) {
        statisticsCounter.countHandlerCall(UPDATE_ANSWER + moduleId + " " + request);

        return moduleControllerMapper
                .mapModuleToModuleResponse(moduleLister
                        .updateModule(moduleId, request), UPDATE_ANSWER);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{moduleId}")
    public void deleteModule(@PathVariable Long moduleId) {
        statisticsCounter.countHandlerCall(DELETE_ANSWER + moduleId);
        moduleLister.deleteModule(moduleId);
    }

    @GetMapping("/{moduleId}/lessons")
    public List<LessonResponse> getLessonsForModule(@PathVariable Long moduleId) {
        statisticsCounter.countHandlerCall("getLessons for module " + moduleId);

        return lessonLister.lessonsByModuleId(moduleId)
                .stream()
                .map(l -> lessonControllerMapper.mapLessonToLessonResponse(l, GET_ANSWER))
                .collect(Collectors.toList());
    }

}
