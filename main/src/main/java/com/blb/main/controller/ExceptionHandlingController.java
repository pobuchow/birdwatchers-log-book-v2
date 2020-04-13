package com.blb.main.controller;

import com.blb.main.service.exception.UserCreationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionHandlingController {

    @ResponseStatus(value= HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(UserCreationException.class)
    private void userCreationFailed(){

    }
}
