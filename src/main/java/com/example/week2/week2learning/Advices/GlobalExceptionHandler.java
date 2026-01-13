package com.example.week2.week2learning.Advices;


import com.example.week2.week2learning.Exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.ResourceAccessException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<MyCustomError> resourceNotFound(ResourceNotFoundException exception){

        String dateAndTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm dd-MMM-yyyy"));
        MyCustomError myCustomError = new MyCustomError(HttpStatus.NOT_FOUND,exception.getMessage(),dateAndTime );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(myCustomError);
//        return new ResponseEntity<>(myCustomError, HttpStatus.NOT_FOUND);
    }
}
