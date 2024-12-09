package com.example.dynamo_sdl;

import com.example.dynamo_sdl.entiry.Customer;
import com.example.dynamo_sdl.entiry.Employee;
import io.awspring.cloud.dynamodb.DynamoDbTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.Page;
import software.amazon.awssdk.enhanced.dynamodb.model.PageIterable;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeDefinition;
import software.amazon.awssdk.services.dynamodb.model.DescribeTableRequest;
import software.amazon.awssdk.services.dynamodb.model.DescribeTableResponse;
import software.amazon.awssdk.services.dynamodb.model.KeySchemaElement;

import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
class AppTests {

    @Autowired
    private DynamoDbTemplate template;

    @Autowired
    private DynamoDbClient dynamoDbClient;

    @Autowired
    private DynamoDbEnhancedClient dynamoDbEnhancedClient;

    Employee employee;

    @BeforeEach
    void init() {
        employee = new Employee("empId", "virendra", "yadav");

    }


    @Test
    void test_1() {
        if (dynamoDbClient.listTables().tableNames()
                .stream()
                .anyMatch(x -> x.equals("Employee"))) {
            template.save(employee);
        } else {
            System.out.println("Table not exists ..");
        }
    }

//    List all tables in DB
    @Test
    void test_2() {
        dynamoDbClient.listTables().tableNames()
                .stream()
                .map(table -> {
                    this.describeTable(table);
                    return table;
                })
                .forEach(System.out::println);
    }

    //	using enhacid client
    @Test
    void test_3() {
        String loginAlias = "one";
        DynamoDbTable<Customer> table = dynamoDbEnhancedClient.table("Customer", TableSchema.fromBean(Customer.class));
        QueryConditional queryConditional = QueryConditional
                .keyEqualTo(k -> k.partitionValue(loginAlias));

        table.query(r -> r.queryConditional(queryConditional))
                .items()
                .stream()
                .collect(Collectors.toList());

    }

    @Test
    void test_4() {
        String LoginAlias = "two";
        // Ensure the table schema matches exactly with the Java POJO
        DynamoDbTable<Customer> table = dynamoDbEnhancedClient.table("Customer", TableSchema.fromBean(Customer.class));
        // Create a query conditional using the partition key (LoginAlias)
        QueryConditional queryConditional = QueryConditional.keyEqualTo(k -> k.partitionValue(LoginAlias));
        // Perform the query and collect results
        table.query(r -> r.queryConditional(queryConditional)).items().stream().collect(Collectors.toList());
    }

    @Test
    void test_5() {
        String loginAlias = "johns";  // Actual value to query
        // Ensure the table schema matches exactly with the Java POJO
        DynamoDbTable<Employee> table = dynamoDbEnhancedClient.table("Employee", TableSchema.fromBean(Employee.class));
        // Create a query conditional using the partition key (LoginAlias)
        QueryConditional queryConditional = QueryConditional.keyEqualTo(k -> k.partitionValue(loginAlias));
        // Perform the query and collect results
        List<Employee> employees = table.query(r -> r.queryConditional(queryConditional))
                .items()
                .stream()
                .collect(Collectors.toList());

        employees.forEach(System.out::println);  // Print results for verification
    }

    @Test
    void test_6() {
        String loginAlias = "LoginAlias";  // Actual value to query
        // Ensure the table schema matches exactly with the Java POJO
        DynamoDbTable<Employee> table = dynamoDbEnhancedClient.table("Employee", TableSchema.fromBean(Employee.class));
        // Create a query conditional using the partition key (LoginAlias)
        QueryConditional queryConditional = QueryConditional.keyEqualTo(k -> k.partitionValue(loginAlias));
        // Perform the query and collect results
        PageIterable<Employee> query = table.query(r -> r.queryConditional(queryConditional));
        Page<Employee> next = query.iterator().next();
        System.out.println(next);

    }

    private void describeTable(String tableName) {
        DescribeTableRequest describeTableRequest = DescribeTableRequest.builder().tableName(tableName).build();
        DescribeTableResponse describeTableResponse = dynamoDbClient.describeTable(describeTableRequest);
        System.out.println("Table: " + tableName);
        System.out.println("Attributes:");
        for (AttributeDefinition attributeDefinition : describeTableResponse.table().attributeDefinitions()) {
            System.out.println(" - " + attributeDefinition.attributeName() + ": " + attributeDefinition.attributeType());
        }
        System.out.println("Key Schema:");
        for (KeySchemaElement keySchemaElement : describeTableResponse.table().keySchema()) {
            System.out.println(" - " + keySchemaElement.attributeName() + ": " + keySchemaElement.keyType());
        }
        System.out.println("Provisioned Throughput:");
        System.out.println(" - Read Capacity: " + describeTableResponse.table().provisionedThroughput().readCapacityUnits());
        System.out.println(" - Write Capacity: " + describeTableResponse.table().provisionedThroughput().writeCapacityUnits());
        System.out.println("Item Count: " + describeTableResponse.table().itemCount());
        System.out.println("Table Size (bytes): " + describeTableResponse.table().tableSizeBytes());
        System.out.println("Table Status: " + describeTableResponse.table().tableStatus());
        System.out.println("-------------------------------------");
    }



}


