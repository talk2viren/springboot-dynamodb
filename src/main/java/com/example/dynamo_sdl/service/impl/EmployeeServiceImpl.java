package com.example.dynamo_sdl.service.impl;

import com.example.dynamo_sdl.entiry.Employee;
import com.example.dynamo_sdl.service.EmployeeService;
import io.awspring.cloud.dynamodb.DynamoDbTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger log = LoggerFactory.getLogger(EmployeeServiceImpl.class);
    private final DynamoDbTemplate dynamoDbTemplate;

    @Autowired
    public EmployeeServiceImpl(DynamoDbTemplate dynamoDbTemplate) {
        this.dynamoDbTemplate = dynamoDbTemplate;
    }

    @Override
    public List<Employee> getAllEmployees() {
        return List.of();
    }

    public Employee createEmployee(Employee employee){
        log.info("Inside Create Employee method >> "+employee);
        return dynamoDbTemplate.save(employee);
    }

    @Override
    public List<Employee> getEmployeeById(String empId) {
        return List.of();
    }

    @Override
    public List<Employee> getEmployeeByLastName(String empId) {
        return List.of();
    }

    @Override
    public List<Employee> updateEmployee(Employee employee) {
        return List.of();
    }

    @Override
    public List<Employee> deleteEmployee(String empId) {
        return List.of();
    }
}
