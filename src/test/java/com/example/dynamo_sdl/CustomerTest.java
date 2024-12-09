package com.example.dynamo_sdl;

import com.example.dynamo_sdl.entiry.Customer;
import com.example.dynamo_sdl.entiry.Employee;
import com.example.dynamo_sdl.entiry.GenericItem;
import io.awspring.cloud.dynamodb.DynamoDbTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;

import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
public class CustomerTest {

    @Autowired
    private DynamoDbTemplate template;

    @Autowired
    private DynamoDbClient dynamoDbClient;

    @Autowired
    private DynamoDbEnhancedClient dynamoDbEnhancedClient;


    private Customer customer;

    @BeforeEach
    void init() {
        customer = new Customer("8", "2003");

    }


    //    Search all tables from AWS
    @Test
    public void test_1() {
        dynamoDbClient.listTables().tableNames()
                .stream()
                .forEach(System.out::println);

    }

    @Test
    public void test_2() {
        template.save(customer);
    }

    @Test
    void test_3() {
        String loginAlias = "johns";
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
        System.out.println("Dynamodb Client");
        DynamoDbTable<Customer> table = dynamoDbEnhancedClient.table("Customer", TableSchema.fromBean(Customer.class));
        Customer item = table.getItem(Key.builder()
                .partitionValue("1")
                .build());
        System.out.println(item);
    }

    @Test
    public void queryEmployeesByCustomerId() {
        String employeeId = "viren";
        DynamoDbTable<Employee> table = dynamoDbEnhancedClient.table("Employee", TableSchema.fromBean(Employee.class));
        QueryConditional queryConditional = QueryConditional.keyEqualTo(k -> k.partitionValue(employeeId));
        List<Employee> collect = table.query(r -> r.queryConditional(queryConditional)).items().stream().collect(Collectors.toList());
        collect.stream().forEach(System.out::println);
    }

@Test
    public void test_5(){
        this.queryTableAsGenericItem("Employee","LoginAlias","viren");
    }
    public void queryTableAsGenericItem(String tableName, String partitionKeyName, String partitionKeyValue) {
        try { // Create a DynamoDbTable object for the specified table
            DynamoDbTable<GenericItem> table = dynamoDbEnhancedClient.table(tableName, TableSchema.fromBean(GenericItem.class));
            // Create a QueryConditional using the partition key
            QueryConditional queryConditional = QueryConditional.keyEqualTo(k -> k.partitionValue(partitionKeyValue));
            // Perform the query and collect results as GenericItem
            List<GenericItem> collect = table.query(r -> r.queryConditional(queryConditional)).items().stream().collect(Collectors.toList());
            collect.stream().forEach(System.out::println);
        } catch (DynamoDbException e) {
            e.printStackTrace();
        }
    }
}