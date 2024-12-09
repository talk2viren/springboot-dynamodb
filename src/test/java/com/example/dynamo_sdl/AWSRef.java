package com.example.dynamo_sdl;

import com.example.dynamo_sdl.entiry.Customer;
import com.example.dynamo_sdl.entiry.MovieDetails;
import com.example.dynamo_sdl.util.DB;
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
import software.amazon.awssdk.enhanced.dynamodb.model.PageIterable;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@SpringBootTest
public class AWSRef {


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
//        getDynamoDBItem(dynamoDbClient, "Customers", "2", "2005");
//        queryDynamoDBWithPartitionKey(dynamoDbClient, "Customers", "customerId", "2");
          DB.queryDynamoDBWithPartitionKey(dynamoDbClient, "Employee", "LoginAlias", "viren");
    }

    //    viren : working create and edit

//    Create Table
    @Test
    public void test_31(){
        DynamoDbEnhancedClient enhancedClient = DynamoDbEnhancedClient.builder().dynamoDbClient(dynamoDbClient).build();
        createTable(enhancedClient);
    }
    @Test
    public void test_3() {
//         final TableSchema<MyCustomer> customerTableSchema = TableSchema.fromBean(MyCustomer.class);
        DynamoDbEnhancedClient enhancedClient = DynamoDbEnhancedClient.builder().dynamoDbClient(dynamoDbClient).build();
//        createTable(enhancedClient);
//        scanAllCustomers(enhancedClient);

        Customer customer1 = new Customer("12345", "2024-12-08");

//        template.save(customer1);
//        PageIterable<Customer> pages = template.scanAll(Customer.class);
//
//        Iterator<Page<Customer>> iterator = pages.iterator();
//        while (iterator.hasNext()){
//            iterator.next().items().stream().forEach(System.out::println);
//        }
/* Working

        System.out.println("Scanning table 'Customer'");
        DynamoDbTable<Customer> customerTable = dynamoDbEnhancedClient.table("Customer", TableSchema.fromBean(Customer.class));
        List<Customer> customers = customerTable.scan().items().stream().collect(Collectors.toList());
        System.out.println("Number of customers found: " + customers.size());
*/

//        PageIterable<Customer> pages = template.scanAll(Customer.class);
//        PageIterable<Customer> one = template.scanAll(Customer.class, "one");
//        pages.iterator().next().items().stream().forEach(System.out::println);

//        Customer one = template.load(Key.builder()
//                .partitionValue("111")
//                .build(), Customer.class);
//        System.out.println(one);



    }

    @Test
    void test_8(){
//        PageIterable<Customer> pages = template.scanAll(Customer.class);
//        System.out.println(pages.iterator().next());

                Customer one = template.load(Key.builder()
                .partitionValue("one")
                .build(), Customer.class);
        System.out.println(one);


    }

    @Test
    void test_9(){
//        template.load(Customer.class, "one", "someDate");
        Customer customer1 = new Customer("12345", "2024-12-08");
        template.save(customer1);
    }

    private static void scanAllCustomers(DynamoDbEnhancedClient enhancedClient) {
        DynamoDbTable<Customer> customerTable = enhancedClient.table("Customer", TableSchema.fromBean(Customer.class)); // Perform the scan operation
        List<Customer> customers = customerTable.scan().items().stream().collect(Collectors.toList()); // Print the results for verification
        customers.forEach(customer -> System.out.println(customer.getCustomerId() + " - " + customer.getDate()));
    }


    private void createTable(DynamoDbEnhancedClient enhancedClient) {
        DynamoDbTable<MovieDetails> movieDetails = enhancedClient.table("MovieDetails", TableSchema.fromBean(MovieDetails.class)); // Create the table
        movieDetails.createTable(CreateTableEnhancedRequest.builder().build());
        System.out.println("Table created successfully.");
    }


    public static void getDynamoDBItem(DynamoDbClient ddb, String tableName, String key, String keyVal) {
        HashMap<String, AttributeValue> keyToGet = new HashMap<>();
        keyToGet.put(key, AttributeValue.builder()
                .s(keyVal)
                .build());

        GetItemRequest request = GetItemRequest.builder()
                .key(keyToGet)
                .tableName(tableName)
                .build();

        try {
            // If there is no matching item, GetItem does not return any data.
            Map<String, AttributeValue> returnedItem = ddb.getItem(request).item();
            if (returnedItem.isEmpty())
                System.out.format("No item found with the key %s!\n", key);
            else {
                Set<String> keys = returnedItem.keySet();
                System.out.println("Amazon DynamoDB table attributes: \n");
                for (String key1 : keys) {
                    System.out.format("%s: %s\n", key1, returnedItem.get(key1).toString());
                }
            }

        } catch (DynamoDbException e) {
//            System.err.println(e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }



}



