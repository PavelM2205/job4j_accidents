package ru.job4j.accidents.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import ru.job4j.accidents.exception.UserWithSuchLoginAlreadyExists;

@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler(UserWithSuchLoginAlreadyExists.class)
    public ModelAndView userWithSuchNameAlreadyExists() {
        ModelAndView mv = new ModelAndView();
        mv.addObject("error",
                "User with such login already exists");
        mv.setViewName("registration");
        return mv;
    }
}
