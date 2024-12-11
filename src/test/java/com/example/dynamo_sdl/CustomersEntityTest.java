package com.example.dynamo_sdl;

import com.example.dynamo_sdl.entiry.Customers;
import com.example.dynamo_sdl.entiry.Employee;
import com.example.dynamo_sdl.entiry.GenericItem;
import com.example.dynamo_sdl.entiry.MovieDetails;
import io.awspring.cloud.dynamodb.DynamoDbTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.CreateTableEnhancedRequest;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.dynamo_sdl.CustomersEntityTest.CustomerUtil.createTable;


@SpringBootTest
public class CustomersEntityTest {

    @Autowired
    private DynamoDbTemplate template;

    @Autowired
    private DynamoDbClient dynamoDbClient;

    @Autowired
    private DynamoDbEnhancedClient dynamoDbEnhancedClient;


    private Customers customers;

    @BeforeEach
    void init() {
//        customer = new Customer("8", "2003");

    }

    @Test
    void createTable_test(){
        createTable(dynamoDbEnhancedClient);
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
        template.save(customers);
    }

    @Test
    void test_3() {
        String loginAlias = "johns";
        DynamoDbTable<Customers> table = dynamoDbEnhancedClient.table("Customer", TableSchema.fromBean(Customers.class));
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
        DynamoDbTable<Customers> table = dynamoDbEnhancedClient.table("Customer", TableSchema.fromBean(Customers.class));
        Customers item = table.getItem(Key.builder()
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

    public static class CustomerUtil {

        private static void scanAllCustomers(DynamoDbEnhancedClient enhancedClient) {
            DynamoDbTable<Customers> customerTable = enhancedClient.table("Customer", TableSchema.fromBean(Customers.class)); // Perform the scan operation
            List<Customers> customers = customerTable.scan().items().stream().collect(Collectors.toList()); // Print the results for verification
            customers.forEach(customer -> System.out.println(customer.getCustomerId() + " - " + customer.getDate()));
        }

        protected static void createTable(DynamoDbEnhancedClient enhancedClient) {
            DynamoDbTable<Customers> customers = enhancedClient.table("Customers", TableSchema.fromBean(Customers.class)); // Create the table
            customers.createTable(CreateTableEnhancedRequest.builder().build());
            System.out.println("Table created successfully.");
        }


    }

}
