package com.mts.teta.courses.handler;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UIHandleErrorService {

    @ExceptionHandler(IllegalArgumentException.class)
    public String illegalArgumentExceptionHandler(IllegalArgumentException ex, Model model) {
        model.addAttribute("title", "Задан не верный параметр");
        model.addAttribute("error", ex.getMessage() + ": Вы ввели недопустимый параметр");
        return "error";
    }

    @ExceptionHandler(AccessDeniedException.class)
    public String accessDeniedExceptionHandler(AccessDeniedException ex, Model model) {
        model.addAttribute("title", "Вам запрещен доступ к этой странице!");
        model.addAttribute("error", ex.getMessage() + ": Доступ к ресурсу запрещен");
        return "error";
    }

    @ExceptionHandler(AuthenticationException.class)
    public String authenticationExceptionHandler(AuthenticationException ex, Model model) {
        model.addAttribute("title", "Вам запрещен доступ к этой странице!");
        model.addAttribute("error", ex.getMessage() + ": Доступ к ресурсу запрещен");
        return "error";
    }

}
