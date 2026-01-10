package com.example.week2.week2learning.Controllers;


import com.example.week2.week2learning.DTO.EmployeeDTO;
import com.example.week2.week2learning.Service.EmployeeService;
import jakarta.validation.Valid;
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
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees(@RequestParam(required = false) Integer age,
                                          @RequestParam(required = false, name="sort") String sortBy){

        return ResponseEntity.ok(empService.getAllEmployees());

    }

    //conversion of java objects into JSON, is done by jackson automatically
    @PostMapping
    public ResponseEntity<EmployeeDTO> createNewEmployee(@RequestBody @Valid EmployeeDTO inputEmployee){

        EmployeeDTO employeeDTO = empService.createNewEmployee(inputEmployee);

        return ResponseEntity.status(HttpStatus.CREATED)
                .header("successful", "successfully created")
                .body(employeeDTO);

    }

    @PutMapping(path="/{empId}")
    public ResponseEntity<EmployeeDTO> updateEmployeeById(@RequestBody @Valid EmployeeDTO employeeDTO,
                                                          @PathVariable Long empId){

        EmployeeDTO updateEmployeeById =  empService.updateEmployeeById(employeeDTO,empId);
        return  ResponseEntity.ok(updateEmployeeById);
    }

    @DeleteMapping(path="/{empId}")
    public ResponseEntity<Boolean> deleteEmployeeById(@PathVariable Long empId){

        boolean deleted =  empService.deleteEmployeeById(empId);

        if(deleted)
            return ResponseEntity.ok(true);
        else
            return ResponseEntity.notFound()
                                 .header("errorReason","id not in DB")
                                 .build();

    }

    @PatchMapping(path="/{empId}")
    public ResponseEntity<EmployeeDTO> updatePartialEmployee(@PathVariable Long empId,
                                             @RequestBody Map<String,Object> updates){
        EmployeeDTO employeeDTO =  empService.updatePartialEmployee(empId,updates);
        if(employeeDTO==null)
            return ResponseEntity.notFound().build();
        else
            return ResponseEntity.ok(employeeDTO);
    }

}
