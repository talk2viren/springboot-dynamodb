package com.example.dynamo_sdl.service.impl;

import com.example.dynamo_sdl.entiry.Employee;
import com.example.dynamo_sdl.service.EmployeeService;
import io.awspring.cloud.dynamodb.DynamoDbTemplate;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.Page;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger log = LoggerFactory.getLogger(EmployeeServiceImpl.class);
    private final DynamoDbTemplate dynamoDbTemplate;
    private final DynamoDbEnhancedClient enhancedClient;

    DynamoDbTable<Employee> dynamoDbTable;

    @Autowired
    public EmployeeServiceImpl(DynamoDbTemplate dynamoDbTemplate, DynamoDbEnhancedClient enhancedClient) {
        this.dynamoDbTemplate = dynamoDbTemplate;
        this.enhancedClient = enhancedClient;
    }

    @PostConstruct
    public void getDynamoDBTable(){
        dynamoDbTable   = enhancedClient.table("Employee", TableSchema
                .fromClass(Employee.class));
    }

    @Override
    public List<Employee> getAllEmployees() {
//        List<Page<Employee>> list = dynamoDbTable.scan().stream().toList();
//        list.stream().
        return null;
    }

    public Employee createEmployee(Employee employee){
        log.info("Inside Create Employee method >> "+employee);
//        return dynamoDbTemplate.save(employee);
        dynamoDbTable.putItem(employee);
        return employee;
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
