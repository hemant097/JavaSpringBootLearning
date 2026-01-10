package com.example.week2.week2learning.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name="employees")// when we want to have table name different from entity class
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String email;
    private Integer age;
    private LocalDate dateOfJoining;
    private Boolean incumbent;
    private Double salary;
    private String role;


}
