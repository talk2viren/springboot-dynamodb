package com.example.dynamo_sdl.service;

import com.example.dynamo_sdl.entiry.Employee;

import java.util.List;

public interface EmployeeService {
    public List<Employee> getAllEmployees();
    public List<Employee> getEmployeeById(String empId);
    public List<Employee> getEmployeeByLastName(String empId);
    public List<Employee> updateEmployee(Employee employee);
    public List<Employee> deleteEmployee(String empId);
    public Employee createEmployee(Employee employee);
}
