package com.example.dynamo_sdl.service.impl;

import com.example.dynamo_sdl.entiry.Employee;
import com.example.dynamo_sdl.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EmployeeServiceImplTest {

    @Autowired
    private EmployeeService service;

    @Test
    void getAllEmployees() {
        long recordCount = service.getAllEmployees().stream().count();
        assertEquals(150,recordCount);
    }

    @Test
    void sortEmployees(){
        service.getAllEmployees().stream().sorted(Comparator.comparing(Employee::getName).thenComparing(Employee::getLastName))
                .forEach(System.out::println);
    }

    @Test
    void createEmployee() {
//        Employee employee=new Employee("viren","virendra","yadav");
//        service.createEmployee(employee);
//
//        Employee employee1=new Employee("viren","virendra","yadav");



        Employee fetchEmp = service.getEmployeeById("brandon.torp","Leopold");
        System.out.println(fetchEmp);
//        assertEquals(employee,employee1);
    }

    @Test
    void test_1(){

        String str="Hello";

        StringBuffer builder=new StringBuffer("Hello");

        System.out.println(str.hashCode());
        System.out.println(builder.toString().hashCode());
    }

    @Test
    void getEmployeeById() {
    }

    @Test
    void getEmployeeByLastName() {
    }

    @Test
    void updateEmployee() {
    }

    @Test
    void deleteEmployee() {
    }
}