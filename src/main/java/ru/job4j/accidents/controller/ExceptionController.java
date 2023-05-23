package ru.job4j.accidents.controller;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice(assignableTypes = RegController.class)
public class ExceptionController {
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ModelAndView userWithSuchNameAlreadyExists() {
        ModelAndView mv = new ModelAndView();
        mv.addObject("error",
                "User with such login already exists");
        mv.setViewName("registration");
        return mv;
    }
}
