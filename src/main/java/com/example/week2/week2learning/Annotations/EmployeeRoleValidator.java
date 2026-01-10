package com.example.week2.week2learning.Annotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class EmployeeRoleValidator implements ConstraintValidator<EmployeeRoleValidation,String> {
    @Override
    public boolean isValid(String inputRole, ConstraintValidatorContext constraintValidatorContext) {
        //can add custom logic for validation
        List<String> possibleRoles = List.of("USER","ADMIN");
        return possibleRoles.contains(inputRole);
    }
}
