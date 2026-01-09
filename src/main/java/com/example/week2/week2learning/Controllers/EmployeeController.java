package com.example.week2.week2learning.Controllers;


import com.example.week2.week2learning.DTO.EmployeeDTO;
import com.example.week2.week2learning.Entity.EmployeeEntity;
import com.example.week2.week2learning.Repository.EmployeeRepository;
import com.example.week2.week2learning.Service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Long empId){

        Optional<EmployeeDTO> employeeDTO = empService.getEmployeeById(empId);

        return employeeDTO
                .map(edto -> ResponseEntity.ok(edto) )
                .orElse(ResponseEntity.notFound()
                        .header("errorReason", "Absent in DB")
                        .build());

        //the above line translates to, if employee dto is not null, send 200 OK, or else create a response with
        //a header(which is optional) and build then return. We can see this response in http response headers in postman
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

    @PatchMapping(path="/{empId}")
    public EmployeeDTO updatePartialEmployee(@PathVariable Long empId,
                                             @RequestBody Map<String,Object> updates){
        return empService.updatePartialEmployee(empId,updates);
    }

}
