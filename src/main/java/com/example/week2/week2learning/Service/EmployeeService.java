package com.example.week2.week2learning.Service;

import com.example.week2.week2learning.DTO.EmployeeDTO;
import com.example.week2.week2learning.Entity.EmployeeEntity;
import com.example.week2.week2learning.Exceptions.ResourceNotFoundException;
import com.example.week2.week2learning.Repository.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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

    public Optional<EmployeeDTO> getEmployeeById(Long empId) {

        Optional<EmployeeEntity> employeeEntity =  empRep.findById(empId);

        //ModelMapper using creating its object
        ModelMapper mapper = new ModelMapper();

        //without using Optional.map()
//        if(employeeEntity.isPresent()){
//            EmployeeEntity ee = employeeEntity.get();
//            EmployeeDTO dto = modelMapper.map(ee,EmployeeDTO.class);
//            return Optional.of(dto);
//        }
//        else
//            return Optional.empty();

        //Optional map() only executes the lambda if a value is present, else returns Optional.empty()
        return employeeEntity.map(emp1 -> mapper.map(emp1,EmployeeDTO.class));
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

    public EmployeeDTO updateEmployeeById(EmployeeDTO employeeDTO, Long empId) {

        whetherEmployeeExists(empId);

        EmployeeEntity employee = modelMapper.map(employeeDTO,EmployeeEntity.class);
        employee.setId(empId);
        EmployeeEntity savedEmployeeEntity = empRep.save(employee);
        return modelMapper.map(savedEmployeeEntity,EmployeeDTO.class);

    }

    public void whetherEmployeeExists(Long empId){
       boolean exists =  empRep.existsById(empId);

       if(!exists)
           throw new ResourceNotFoundException("this employee does not exist with id "+empId);

    }

    public boolean deleteEmployeeById(Long empId) {
       whetherEmployeeExists(empId);

            empRep.deleteById(empId);
            return true;

    }

    public EmployeeDTO updatePartialEmployee(Long empId, Map<String, Object> updates) {
        whetherEmployeeExists(empId);

        EmployeeEntity employeeEntity = empRep.findById(empId).get();

        //using reflection, picking each field matching from updates, and setting accessibility to public
        updates.forEach((field,value)->{
            Field fieldToBeUpdated = ReflectionUtils.findField(EmployeeEntity.class,field);
            fieldToBeUpdated.setAccessible(true);
            //modifying the field of employeeEntity using the fieldToBeUpdated, and value from updates
            ReflectionUtils.setField(fieldToBeUpdated,employeeEntity,value);
        });

//        EmployeeEntity updatedEmployee = empRep.save(employeeEntity);
//        return modelMapper.map(updatedEmployee,EmployeeDTO.class);

        //same as above 2 commented lines
        return modelMapper.map(empRep.save(employeeEntity),EmployeeDTO.class);



    }
}
