package com.mts.teta.courses.controller;

import com.mts.teta.courses.dto.ModuleRequestToCreate;
import com.mts.teta.courses.dto.ModuleRequestToUpdate;
import com.mts.teta.courses.dto.ModuleResponse;
import com.mts.teta.courses.mapper.ModuleControllerMapper;
import com.mts.teta.courses.service.ModuleLister;
import com.mts.teta.courses.service.StatisticsCounter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/module")
public class ModuleController {
    private final static String GET_ANSWER = "Module found";
    private final static String UPDATE_ANSWER = "Module Updated";
    private final static String CREATE_ANSWER = "Module Created";
    private final static String DELETE_ANSWER = "Module Deleted";

    @Autowired
    private ModuleLister ModuleLister;
    @Autowired
    private StatisticsCounter statisticsCounter;
    @Autowired
    private ModuleControllerMapper ModuleControllerMapper;

    @GetMapping("/{moduleId}")
    public ModuleResponse getModule(@PathVariable("moduleId") Long moduleId) {
        statisticsCounter.countHandlerCall("getModule " + moduleId);
        return ModuleControllerMapper.mapModuleToModuleResponse(ModuleLister.moduleById(moduleId), GET_ANSWER);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping
    public ModuleResponse createModule(@Valid @RequestBody ModuleRequestToCreate request) {
        statisticsCounter.countHandlerCall(CREATE_ANSWER + request);

        return ModuleControllerMapper
                .mapModuleToModuleResponse(ModuleLister
                        .createModule(request), CREATE_ANSWER);
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("/{moduleId}")
    public ModuleResponse updateModule(@PathVariable Long moduleId,
                                       @Valid @RequestBody ModuleRequestToUpdate request) {
        statisticsCounter.countHandlerCall(UPDATE_ANSWER + moduleId + " " + request);

        return ModuleControllerMapper
                .mapModuleToModuleResponse(ModuleLister
                        .updateModule(moduleId, request), UPDATE_ANSWER);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{moduleId}")
    public void deleteModule(@PathVariable Long moduleId) {
        statisticsCounter.countHandlerCall(DELETE_ANSWER + moduleId);
        ModuleLister.deleteModule(moduleId);
    }

}
