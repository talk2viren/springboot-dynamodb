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
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;

import java.util.*;
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
    public void test_31() {
        DynamoDbEnhancedClient enhancedClient = DynamoDbEnhancedClient.builder().dynamoDbClient(dynamoDbClient).build();
        Util.createTable(enhancedClient);
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
    void test_8() {
//        PageIterable<Customer> pages = template.scanAll(Customer.class);
//        System.out.println(pages.iterator().next());

        Customer one = template.load(Key.builder()
                .partitionValue("one")
                .build(), Customer.class);
        System.out.println(one);


    }

    @Test
    void test_9() {
//        template.load(Customer.class, "one", "someDate");
        Customer customer1 = new Customer("12345", "2024-12-08");
        template.save(customer1);
    }

//    Throwing Index out of bound exception
    @Test
    void test_10() {
        List<AttributeValue> attributeValueList = new ArrayList<>();
        attributeValueList.add(AttributeValue.builder().s("Name1").build());
        attributeValueList.add(AttributeValue.builder().s("Name2").build());
        attributeValueList.add(AttributeValue.builder().s("Name3").build());
        attributeValueList.add(AttributeValue.builder().s("Name4").build());
        attributeValueList.add(AttributeValue.builder().s("Name5").build());
        Map<String, AttributeValue> map = new HashMap<>();
        map.put("one", AttributeValue.builder().l(attributeValueList).build());

//        Map<String, Object> stringObjectMap = Util.convertAttributeValueMap(map);
        Map<String, Object> stringObjectMap = AttributeValueConverter.convertAttributeValueMap(map);

        Customer customer1 = new Customer("cust_1", "2024", stringObjectMap);
        template.save(customer1);


    }


    public interface Util {

        private static void scanAllCustomers(DynamoDbEnhancedClient enhancedClient) {
            DynamoDbTable<Customer> customerTable = enhancedClient.table("Customer", TableSchema.fromBean(Customer.class)); // Perform the scan operation
            List<Customer> customers = customerTable.scan().items().stream().collect(Collectors.toList()); // Print the results for verification
            customers.forEach(customer -> System.out.println(customer.getCustomerId() + " - " + customer.getDate()));
        }


        private static void createTable(DynamoDbEnhancedClient enhancedClient) {
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


}



class AttributeValueConverter {
    public static Map<String, Object> convertAttributeValueMap(Map<String, AttributeValue> attributeValueMap) {
        Map<String, Object> resultMap = new HashMap<>();
        for (Map.Entry<String, AttributeValue> entry : attributeValueMap.entrySet()) {
            resultMap.put(entry.getKey(), convertAttributeValue(entry.getValue()));
        }
        return resultMap;
    }

    private static Object convertAttributeValue(AttributeValue attributeValue) {
        if (attributeValue.s() != null) {
            return attributeValue.s();
        } else if (attributeValue.n() != null) {
            return attributeValue.n();
        } else if (attributeValue.bool() != null) {
            return attributeValue.bool();
        } else if (attributeValue.m() != null) {
            return convertAttributeValueMap(attributeValue.m());
        } else if (attributeValue.l() != null) {
            return attributeValue.l().stream().map(AttributeValueConverter::convertAttributeValue).collect(Collectors.toList());
        } else if (attributeValue.ss() != null) {
            return attributeValue.ss();
        } else if (attributeValue.ns() != null) {
            return attributeValue.ns();
        } else if (attributeValue.bs() != null) {
            return attributeValue.bs();
        }
        return null;
    }

    public static Map<String, AttributeValue> convertObjectMapToAttributeValueMap(Map<String, Object> objectMap) {
        Map<String, AttributeValue> resultMap = new HashMap<>();
        for (Map.Entry<String, Object> entry : objectMap.entrySet()) {
            resultMap.put(entry.getKey(), convertObjectToAttributeValue(entry.getValue()));
        }
        return resultMap;
    }

    private static AttributeValue convertObjectToAttributeValue(Object object) {
        if (object instanceof String) {
            return AttributeValue.builder().s((String) object).build();
        } else if (object instanceof Number) {
            return AttributeValue.builder().n(object.toString()).build();
        } else if (object instanceof Boolean) {
            return AttributeValue.builder().bool((Boolean) object).build();
        } else if (object instanceof Map) {
            return AttributeValue.builder().m(convertObjectMapToAttributeValueMap((Map<String, Object>) object)).build();
        } else if (object instanceof List) {
            return AttributeValue.builder().l(((List<?>) object).stream().map(AttributeValueConverter::convertObjectToAttributeValue).collect(Collectors.toList())).build();
        }
        return null;
    }
}