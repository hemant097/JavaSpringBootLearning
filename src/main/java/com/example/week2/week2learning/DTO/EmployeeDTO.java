package com.example.week2.week2learning.DTO;

import java.time.LocalDate;

@SuppressWarnings("all")
public class EmployeeDTO {

    Long id;
    String name;
    String email;
    Integer age;
    LocalDate dateOfJoining;
    Boolean incumbent;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public LocalDate getDateOfJoining() {
        return dateOfJoining;
    }

    public void setDateOfJoining(LocalDate dateOfJoining) {
        this.dateOfJoining = dateOfJoining;
    }

    public Boolean getIncumbent() {
        return incumbent;
    }

    public void setIncumbent(Boolean incumbent) {
        this.incumbent = incumbent;
    }

    public EmployeeDTO(Long id, String name, String email, Integer age, LocalDate dateOfJoining, Boolean incumbent) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
        this.dateOfJoining = dateOfJoining;
        this.incumbent = incumbent;
    }

    public EmployeeDTO(){}
}
