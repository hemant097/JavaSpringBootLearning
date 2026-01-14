package com.example.week2.week2learning.Advices;

import com.example.week2.week2learning.Exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<MyCustomError> resourceNotFound(ResourceNotFoundException rnfe) {

        String dateAndTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm"));
        //using lombok package private constructor
        MyCustomError myCustomError = new MyCustomError(HttpStatus.NOT_FOUND,rnfe.getMessage(),dateAndTime );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(myCustomError);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<MyCustomError> internalServerError(MethodArgumentNotValidException manve) {

        //getting all the binding errors from the exception and converting it to List<String> using stream
        List<String> errorList = manve.getBindingResult()
                .getAllErrors()
                .stream().map(error->error.getDefaultMessage())
                .collect(Collectors.toList());

        String dateAndTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm"));
        //using lombok builder
        MyCustomError myCustomError = MyCustomError.builder()
                .message(errorList.toString())
                .httpStatus(HttpStatus.BAD_REQUEST)
                .errorRecordedTime(dateAndTime)
                .build();
        return new ResponseEntity<>(myCustomError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<MyCustomError> internalServerError(Exception exception) {

        String dateAndTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm"));
        //using lombok builder
        MyCustomError myCustomError = MyCustomError.builder()
                .message(exception.getMessage())
                .errorRecordedTime(dateAndTime)
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();

        return new ResponseEntity<>(myCustomError, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}