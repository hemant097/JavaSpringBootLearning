package com.example.week2.week2learning.Config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    //to handle constructor injection in EmployeeService, also to implement DRY
    @Bean
    public ModelMapper createMapper(){
        return new ModelMapper();
    }
}
