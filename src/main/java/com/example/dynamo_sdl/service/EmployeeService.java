package com.example.dynamo_sdl.service;

import com.example.dynamo_sdl.entiry.Employee;

import java.util.List;

public interface EmployeeService {
    public List<Employee> getAllEmployees();
    public Employee getEmployeeById(String empId,String sort);
    public List<Employee> getEmployeeByLastName(String empId);
    public List<Employee> updateEmployee(Employee employee);
    public List<Employee> deleteEmployee(String empId);
    public Employee createEmployee(Employee employee);
}
