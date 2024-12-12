package com.example.dynamo_sdl.entity;

import com.example.dynamo_sdl.entiry.Customers;
import com.example.dynamo_sdl.entiry.Employee;
import io.awspring.cloud.dynamodb.DynamoDbTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.CreateTableEnhancedRequest;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
public class EmployeeTest {

//    @Autowired
//    protected final DynamoDbClient dynamoDbClient;
//
//    @Autowired
//    protected final DynamoDbEnhancedClient dynamoDbEnhancedClient;
//
//    @Autowired
//    protected final DynamoDbTemplate dynamoDbTemplate;
//
//    @Autowired
//    public EmployeeTest(DynamoDbClient dynamoDbClient, DynamoDbEnhancedClient dynamoDbEnhancedClient, DynamoDbTemplate dynamoDbTemplate) {
//        this.dynamoDbClient = dynamoDbClient;
//        this.dynamoDbEnhancedClient = dynamoDbEnhancedClient;
//        this.dynamoDbTemplate = dynamoDbTemplate;
//    }
//
//    DynamoDbTable<Employee> dynamoDbTable = null;

//    @BeforeEach
//    void init() {
//        dynamoDbTable = dynamoDbEnhancedClient.table("Employee", TableSchema.fromBean(Employee.class)); // Create the table
//    }


    public static class EmployeeUtil {

        private static void scanAllCustomers(DynamoDbEnhancedClient enhancedClient) {
            DynamoDbTable<Customers> customerTable = enhancedClient.table("Customer", TableSchema.fromBean(Customers.class)); // Perform the scan operation
            List<Customers> customers = customerTable.scan().items().stream().collect(Collectors.toList()); // Print the results for verification
            customers.forEach(customer -> System.out.println(customer.getCustomerId() + " - " + customer.getDate()));
        }

        protected static void createTable(DynamoDbEnhancedClient enhancedClient) {
            DynamoDbTable<Employee> employee = enhancedClient.table("MyEmployee", TableSchema.fromBean(Employee.class)); // Create the table
            employee.createTable(CreateTableEnhancedRequest.builder().build());
            System.out.println("Employee Table created successfully.");
        }


    }

}
