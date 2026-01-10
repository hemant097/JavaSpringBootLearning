package com.example.week2.week2learning.Advices;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> resourceNotFound(NoSuchElementException exception){

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
//        return new ResponseEntity<>("Resource not found", HttpStatus.NOT_FOUND);
    }
}
