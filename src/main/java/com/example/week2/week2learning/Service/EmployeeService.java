package com.example.week2.week2learning.Service;

import com.example.week2.week2learning.DTO.EmployeeDTO;
import com.example.week2.week2learning.Entity.EmployeeEntity;
import com.example.week2.week2learning.Repository.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private final EmployeeRepository empRep;
    private final ModelMapper modelMapper;

    //Constructor injection
    public EmployeeService(EmployeeRepository empRep, ModelMapper modelMapper) {
        this.empRep = empRep;
        this.modelMapper =modelMapper;
    }

    public EmployeeDTO getEmployeeById(Long empId) {
        EmployeeEntity employeeEntity =  empRep.findById(empId).orElse(null);

        //ModelMapper using creating its object
        ModelMapper mapper = new ModelMapper();
        return mapper.map(employeeEntity,EmployeeDTO.class);
    }

    public List<EmployeeDTO> getAllEmployees() {

        List<EmployeeEntity> employeeEntityList = empRep.findAll();

        //using forEach , we have to create a new EmployeeDTO List
//        List<EmployeeDTO> employeeDTOList = new ArrayList<>();
//        for(EmployeeEntity empEnt: employeeEntityList)
//            employeeDTOList.add(modelMapper.map(empEnt,EmployeeDTO.class));

        //Stream is better
        return employeeEntityList
                .stream()
                .map(empEntity -> modelMapper.map(empEntity,EmployeeDTO.class))
                .collect(Collectors.toList());

    }

    public EmployeeDTO createNewEmployee(EmployeeDTO inputEmployee) {

        //mapping EmployeeDTO to EmployeeEntity
        EmployeeEntity toMapEmployee = modelMapper.map(inputEmployee, EmployeeEntity.class);

        //saving to DB
        EmployeeEntity savedEmployee = empRep.save(toMapEmployee);

        //returning EmployeeDTO using modelMapper
        return modelMapper.map(savedEmployee,EmployeeDTO.class);
    }
}
