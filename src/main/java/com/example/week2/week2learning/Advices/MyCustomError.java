package com.example.week2.week2learning.Advices;


import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Builder
@Getter
public class MyCustomError {
    private  HttpStatus httpStatus;
    private  String message;
    private  String errorRecordedTime;
}