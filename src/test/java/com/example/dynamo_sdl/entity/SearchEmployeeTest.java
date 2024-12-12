package com.example.dynamo_sdl.entity;

import com.example.dynamo_sdl.entiry.Employee;
import io.awspring.cloud.dynamodb.DynamoDbTemplate;
import org.junit.jupiter.api.BeforeEach;
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
import java.util.Map;
import java.util.stream.Collectors;

@SpringBootTest
public class SearchEmployeeTest extends EmployeeTest {

    @Autowired
    protected DynamoDbClient dynamoDbClient;

    @Autowired
    protected DynamoDbEnhancedClient dynamoDbEnhancedClient;

    @Autowired
    protected DynamoDbTemplate dynamoDbTemplate;

    @Autowired
    public SearchEmployeeTest(DynamoDbClient dynamoDbClient, DynamoDbEnhancedClient dynamoDbEnhancedClient, DynamoDbTemplate dynamoDbTemplate) {
        this.dynamoDbClient = dynamoDbClient;
        this.dynamoDbEnhancedClient = dynamoDbEnhancedClient;
        this.dynamoDbTemplate = dynamoDbTemplate;
    }

    DynamoDbTable<Employee> dynamoDbTable = null;

    @BeforeEach
    void init() {
        dynamoDbTable = dynamoDbEnhancedClient.table("Employee", TableSchema.fromBean(Employee.class)); // Create the table
    }


//    public SearchEmployeeTest(DynamoDbClient dynamoDbClient, DynamoDbEnhancedClient dynamoDbEnhancedClient, DynamoDbTemplate dynamoDbTemplate) {
//        super(dynamoDbClient, dynamoDbEnhancedClient, dynamoDbTemplate);
//    }


//    Search Table Test3 onwards

    @Test
    void enhanceClient_test_1() {
//        table.scan().stream().forEach(System.out::println);
        Map<String, Long> longMap = dynamoDbTable.scan().stream().flatMap(employeePage -> employeePage.items().stream()).collect(Collectors.groupingBy(Employee::getLastName, Collectors.counting()));

//        longMap.entrySet().stream()
//                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
//                        .forEach(System.out::println);


//        Map<String, Long> sortedMap = longMap.entrySet().stream()
//                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
//                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, // If there are duplicate keys, keep the first one
//                         LinkedHashMap::new // Maintain the order
//                         ));

        List<Page<Employee>> list = dynamoDbTable.scan(ScanEnhancedRequest.builder().limit(10).build()).stream().toList();

        PageIterable<Employee> scan = dynamoDbTable.scan(x -> x.limit(10).build());
        System.out.println("Size : " + scan.stream().count());

        for (Page<Employee> page : scan) {
//            System.out.println(page.);

        }
//        List<Page<Employee>> list = table.scan().stream().toList();

/*
        List<Page<Employee>> empList = list.stream().map(employeePage -> {
            System.out.println(employeePage.items().size());
            return employeePage;
        }).toList();
*/

//        int size = list.stream().flatMap(employeePage -> employeePage.items().stream())
//                .toList().size();

//        System.out.println(size);

    }

    @Test
    void enhanceClient_test_2() {
        QueryEnhancedRequest build = QueryEnhancedRequest.builder().queryConditional(QueryConditional.keyEqualTo(Key.builder().partitionValue("kylie.haley").build())).build();
        PageIterable<Employee> query = dynamoDbTable.query(build);
        query.stream().forEach(System.out::println);
    }


    @Test
    void dynamodbTemplate_test_1() {
//        dynamoDbTemplate.save(employee1);
        dynamoDbTemplate.scanAll(Employee.class).stream().forEach(System.out::println);

    }


    @Test
    void dynamodbTemplate_test_2() {
        PageIterable<Employee> query = dynamoDbTemplate.query(QueryEnhancedRequest.builder().queryConditional(QueryConditional.keyEqualTo(Key.builder().partitionValue("kylie.haley").sortValue("Tracey").build()))
//                .addAttributeToProject("mylogin")
                .build(), Employee.class);
        query.stream().forEach(System.out::println);
    }

}
