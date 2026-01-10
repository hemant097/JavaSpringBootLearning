package com.example.week2.week2learning.Annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {EmployeeRoleValidator.class}) // “explicitly stating use this validator class for this constraint.”
public @interface EmployeeRoleValidation {

    String message() default "Enter correct role in UPPERCASE";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
