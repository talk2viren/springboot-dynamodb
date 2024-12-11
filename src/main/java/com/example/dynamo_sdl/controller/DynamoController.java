package com.example.dynamo_sdl.controller;

import com.example.dynamo_sdl.entiry.Employee;
import com.example.dynamo_sdl.service.EmployeeService;
import io.awspring.cloud.dynamodb.DynamoDbTemplate;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.util.Collections;
import java.util.List;


import static java.util.Collections.emptyList;

/*
    Create Employee: POST /api/employees
    Update Employee: PUT /api/employees/123
    Delete Employee: DELETE /api/employees/123
    Search Employee by ID: GET /api/employees/123
    Search Employee by Name: GET /api/employees/search?name=John
    Get All Employees: GET /api/employees
*/

@RestController
@RequestMapping("/api")

public class DynamoController {

    private static final Logger log = LoggerFactory.getLogger(DynamoController.class);

    private EmployeeService employeeService;

    @Autowired
    public DynamoController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/employees")
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee){
        log.info("Employee >>> "+employee);

        return ResponseEntity.ok(employeeService.createEmployee(employee));
    }

    @PutMapping("/employees/{name}")
    public ResponseEntity<?> getEmployee(@PathVariable String name){
        log.info("Put Employee >> "+name);
//        Employee employee =new Employee("empId","virendra","yadav");

        return ResponseEntity.ok(List.of("name1","name2"));
    }

    @DeleteMapping("/employees/{empId}")
    public ResponseEntity<Employee> deleteEmployee(@PathVariable String empId){
        log.info("Delete Employee >> "+empId);
        return ResponseEntity.ok(new Employee());
    }

    @GetMapping("/employees/{employeeName}")
    public ResponseEntity<List<Employee>> searchEmployeeByName(@PathVariable String employeeName){
        log.info("searchEmployeeByName >> "+employeeName);
        return ResponseEntity.ok(Collections.emptyList());
    }

    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> getAllEmployees(){
        log.info("Inside getAllEmployees ");
        return ResponseEntity.ok(Collections.emptyList());
    }


}
