package com.example.week2.week2learning.Controllers;


import com.example.week2.week2learning.DTO.EmployeeDTO;
import com.example.week2.week2learning.Entity.EmployeeEntity;
import com.example.week2.week2learning.Repository.EmployeeRepository;
import com.example.week2.week2learning.Service.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(path = "/employees" )
public class EmployeeController {

    private final EmployeeService empService;

    //Constructor injection,
    public EmployeeController(EmployeeService employeeService) {
        this.empService = employeeService;
    }

//    @GetMapping(path="/value")
//    @ResponseBody ,only when @Controller is used
//    public String getMyMessage(){
//        return "my secret jh*&^&&$&";
//    }

    //Path Variable, strictly mandatory
    @GetMapping(path = "/{empId}")
    public EmployeeDTO getEmployeeById(@PathVariable Long empId){

//        return new EmployeeDTO(empId,"Hemant","abx@example.com",27,LocalDate.now(),true);

        return empService.getEmployeeById(empId);

    }

    //RequestParam, not strictly mandatory, when required=false
    @GetMapping(path = "/all")
    public List<EmployeeDTO> getAllEmployees(@RequestParam(required = false) Integer age,
                                          @RequestParam(required = false, name="sort") String sortBy){
//        return "Hi customer "+age+" "+sortBy;

        return empService.getAllEmployees();

    }

//    @PostMapping
//    public String createNewEmployee(){
//        return "Hello from POST method";
//    }

    //conversion of java objects into JSON, is done by jackson automatically
    @PostMapping
    public EmployeeDTO createNewEmployee(@RequestBody EmployeeDTO inputEmployee){
//        inputEmployee.setId(100L);
//        return inputEmployee;

        return empService.createNewEmployee(inputEmployee);

    }

    @PutMapping(path="/{empId}")
    public EmployeeDTO updateEmployeeById(@RequestBody EmployeeDTO employeeDTO,@PathVariable Long empId){
        return empService.updateEmployeeById(employeeDTO,empId);
    }

    @DeleteMapping(path="/{empId}")
    public boolean deleteEmployeeById(@PathVariable Long empId){
        return empService.deleteEmployeeById(empId);
    }

}
