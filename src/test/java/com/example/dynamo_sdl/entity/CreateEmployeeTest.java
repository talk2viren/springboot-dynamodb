package com.example.dynamo_sdl.entity;

import com.example.dynamo_sdl.entiry.Customers;
import com.example.dynamo_sdl.entiry.Employee;
import com.github.javafaker.Faker;
import io.awspring.cloud.dynamodb.DynamoDbTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import static com.example.dynamo_sdl.util.DB.getDynamoDBItem;
import static com.example.dynamo_sdl.util.DB.queryDynamoDBWithPartitionKey;

@SpringBootTest
public class CreateEmployeeTest extends EmployeeTest {


    @Autowired
    protected final DynamoDbClient dynamoDbClient;

    @Autowired
    protected final DynamoDbEnhancedClient dynamoDbEnhancedClient;

    @Autowired
    protected final DynamoDbTemplate dynamoDbTemplate;

    @Autowired
    public CreateEmployeeTest(DynamoDbClient dynamoDbClient, DynamoDbEnhancedClient dynamoDbEnhancedClient, DynamoDbTemplate dynamoDbTemplate) {
        this.dynamoDbClient = dynamoDbClient;
        this.dynamoDbEnhancedClient = dynamoDbEnhancedClient;
        this.dynamoDbTemplate = dynamoDbTemplate;
    }

    DynamoDbTable<Employee> dynamoDbTable = null;

    @BeforeEach
    void init() {
        dynamoDbTable = dynamoDbEnhancedClient.table("Employee", TableSchema.fromBean(Employee.class)); // Create the table
    }


//    public CreateEmployeeTest(DynamoDbClient dynamoDbClient, DynamoDbEnhancedClient dynamoDbEnhancedClient, DynamoDbTemplate dynamoDbTemplate) {
//        super(dynamoDbClient, dynamoDbEnhancedClient, dynamoDbTemplate);
//    }

    @Test
    void delete_table() {
        dynamoDbTable.deleteTable();
    }

    @Test
    void createTable_test() {
//        Create table with different table name then of entity
//        DynamoDbTable<Employee> employee = dynamoDbEnhancedClient.table("MyEmployee", TableSchema.fromBean(Employee.class)); // Create the table

//        employee.createTable(CreateTableEnhancedRequest.builder().build());
        dynamoDbTable.createTable();
        System.out.println("Employee Table created successfully.");
    }

    @Test
    void data_import() {

        Faker faker = Faker.instance();
        for (int i = 0; i < 150; i++) {
            Employee employeePojo = new Employee(faker.name().username(), faker.name().firstName(), faker.name().lastName());
            dynamoDbTable.putItem(employeePojo);
        }
    }

    //  DynamoddbEnhanced Client  Delete Tale
    @Test
    void deleteTable() {
        dynamoDbTable.deleteTable();
        System.out.println("Table Deleted");
    }

    //    List All tables
    @Test
    void test_1() {
        dynamoDbClient.listTables().tableNames().stream().forEach(System.out::println);
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


}

