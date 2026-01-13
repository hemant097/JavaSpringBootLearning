package com.example.week2.week2learning.Advices;


import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
public class MyCustomError {

    private final HttpStatus httpStatus;
    private final String string;
    private final String localDateTime;
}
