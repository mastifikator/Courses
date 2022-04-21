package com.mts.teta.courses.handler;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UIHandleErrorService {

    @ExceptionHandler(IllegalArgumentException.class)
    public String illegalArgumentExceptionHandler(IllegalArgumentException ex, Model model) {
        model.addAttribute("title", "Произошла ошибка!");
        model.addAttribute("error", ex.getMessage());
        return "error";
    }
}
