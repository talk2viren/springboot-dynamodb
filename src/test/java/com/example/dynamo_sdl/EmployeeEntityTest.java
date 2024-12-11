package com.example.dynamo_sdl;

import com.example.dynamo_sdl.entiry.Customers;
import com.example.dynamo_sdl.entiry.Employee;
import com.example.dynamo_sdl.entiry.MovieDetails;
import io.awspring.cloud.dynamodb.DynamoDbTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.*;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.dynamo_sdl.EmployeeEntityTest.EmployeeUtil.createTable;
import static com.example.dynamo_sdl.util.DB.getDynamoDBItem;
import static com.example.dynamo_sdl.util.DB.queryDynamoDBWithPartitionKey;

@SpringBootTest
public class EmployeeEntityTest {

    private final DynamoDbClient dynamoDbClient;

    private final DynamoDbEnhancedClient dynamoDbEnhancedClient;

    private final DynamoDbTemplate dynamoDbTemplate;

    @Autowired
    public EmployeeEntityTest(DynamoDbClient dynamoDbClient, DynamoDbEnhancedClient dynamoDbEnhancedClient, DynamoDbTemplate dynamoDbTemplate) {
        this.dynamoDbClient = dynamoDbClient;
        this.dynamoDbEnhancedClient = dynamoDbEnhancedClient;
        this.dynamoDbTemplate = dynamoDbTemplate;
    }

//    @Test
    void createTable_test(){
//        Create table with different table name then of entity
//        DynamoDbTable<Employee> employee = dynamoDbEnhancedClient.table("MyEmployee", TableSchema.fromBean(Employee.class)); // Create the table

        DynamoDbTable<Employee> employee = dynamoDbEnhancedClient.table("Employee", TableSchema.fromBean(Employee.class)); // Create the table
//        employee.createTable(CreateTableEnhancedRequest.builder().build());
        employee.createTable();
        System.out.println("Employee Table created successfully.");
    }

//  DynamoddbEnhanced Client  Delete Tale
    @Test
    void  deleteTable(){
        DynamoDbTable<Employee> dynamoDbTable = dynamoDbEnhancedClient.table("MyEmployee", TableSchema.fromClass(Employee.class));
        dynamoDbTable.deleteTable();
        System.out.println("Table Deleted");
    }

//    List All tables
    @Test
    void test_1() {
        dynamoDbClient.listTables().tableNames().stream().forEach(System.out::println);
    }

// Put Item using Enhanced Client
    @Test
    void test_2() {

        Employee employee1 = new Employee("mylogin2", "myname", "");
        DynamoDbTable<Employee> table = dynamoDbEnhancedClient.table("Employee", TableSchema.fromBean(Employee.class));

        table.putItem(employee1);




//        Employee emp = employee.getItem(GetItemEnhancedRequest.builder()
//                .key(Key.builder()
//                        .partitionValue("mylogin")
//                        .sortValue("myname")
//                        .build())
//                .build());
//        System.out.println(emp);

//        Employee emp = employee.getItem(GetItemEnhancedRequest.builder().key(Key.builder().partitionValue("mylogin") // Ensure this matches the partition key value
//                .sortValue("myname") // Ensure this matches the sort key value
//                .build()).build());

//        System.out.println(one);
    }

//    Scan emp table
    @Test
    void test_21(){
        DynamoDbTable<Employee> table = dynamoDbEnhancedClient.table("Employee", TableSchema.fromBean(Employee.class));
        table.scan().stream().forEach(System.out::println);
    }

    @Test
    void test_23(){
        Employee employee1 = new Employee("mylogin21", "myname", "");

//        dynamoDbTemplate.save(employee1);
        dynamoDbTemplate.scanAll(Employee.class).stream().forEach(System.out::println);

    }


        @Test
    void test_22(){
        PageIterable<Employee> query = dynamoDbTemplate.query(QueryEnhancedRequest.builder()
                        .queryConditional(QueryConditional.keyEqualTo(Key.builder().partitionValue("mylogin").build()))
//                .addAttributeToProject("mylogin")
                .build(), Employee.class);
        query.stream().forEach(System.out::println);
    }

    @Test
    void test_3() {
        Customers customers = new Customers("custName", "2024");
        dynamoDbTemplate.save(customers);
//        dynamoDbEnhancedClient.
    }

    @Test
    public void test_4() {
        getDynamoDBItem(dynamoDbClient, "Customers", "2", "2005");
        queryDynamoDBWithPartitionKey(dynamoDbClient, "Customers", "customerId", "2");
        queryDynamoDBWithPartitionKey(dynamoDbClient, "Employee", "LoginAlias", "viren");
    }


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

