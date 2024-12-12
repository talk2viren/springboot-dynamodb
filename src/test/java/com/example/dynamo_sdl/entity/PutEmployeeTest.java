package com.example.dynamo_sdl.entity;

import com.example.dynamo_sdl.entiry.Employee;
import io.awspring.cloud.dynamodb.DynamoDbTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.util.stream.IntStream;

public class PutEmployeeTest extends EmployeeTest{
    @Autowired
    protected final DynamoDbClient dynamoDbClient;

    @Autowired
    protected final DynamoDbEnhancedClient dynamoDbEnhancedClient;

    @Autowired
    protected final DynamoDbTemplate dynamoDbTemplate;

    @Autowired
    public PutEmployeeTest(DynamoDbClient dynamoDbClient, DynamoDbEnhancedClient dynamoDbEnhancedClient, DynamoDbTemplate dynamoDbTemplate) {
        this.dynamoDbClient = dynamoDbClient;
        this.dynamoDbEnhancedClient = dynamoDbEnhancedClient;
        this.dynamoDbTemplate = dynamoDbTemplate;
    }

    DynamoDbTable<Employee> dynamoDbTable = null;

    @BeforeEach
    void init() {
        dynamoDbTable = dynamoDbEnhancedClient.table("Employee", TableSchema.fromBean(Employee.class)); // Create the table
    }


//    public PutEmployeeTest(DynamoDbClient dynamoDbClient, DynamoDbEnhancedClient dynamoDbEnhancedClient, DynamoDbTemplate dynamoDbTemplate) {
//        super(dynamoDbClient, dynamoDbEnhancedClient, dynamoDbTemplate);
//    }

    // Put Item using Enhanced Client
    @Test
    void test_2() {

        DynamoDbTable<Employee> table = dynamoDbEnhancedClient.table("Employee", TableSchema.fromBean(Employee.class));


        IntStream.range(1, 100).boxed().map(x -> {
            Employee employee1 = new Employee(x.toString(), "myname", x.toString());
            table.putItem(employee1);
            return x;
        }).toList();


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

}
