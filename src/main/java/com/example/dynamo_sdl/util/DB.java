package com.example.dynamo_sdl.util;

import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DB {

    public static void queryDynamoDBWithPartitionKey(DynamoDbClient ddb, String tableName, String
            partitionKeyName, String partitionKeyValue) {
        // Construct the query parameters using the partition key value
        HashMap<String, AttributeValue> keyToGet = new HashMap<>();
        keyToGet.put(":" + partitionKeyName, AttributeValue.builder().s(partitionKeyValue).build());
        // Build the QueryRequest with the key condition expression
        QueryRequest request = QueryRequest.builder().tableName(tableName).keyConditionExpression(partitionKeyName + " = :" + partitionKeyName).expressionAttributeValues(keyToGet).build();
        try { // Execute the query and get the response
            QueryResponse response = ddb.query(request);
            List<Map<String, AttributeValue>> items = response.items();
            if (items.isEmpty()) {
                System.out.format("No item found with the partition key %s: %s\n", partitionKeyName, partitionKeyValue);
            } else {
                System.out.println("Amazon DynamoDB table attributes: \n");
                for (Map<String, AttributeValue> item : items) {
                    Set<String> keys = item.keySet();
                    for (String key : keys) {
                        System.out.format("%s: %s\n", key, item.get(key).toString());
                    }
                }
            }
        } catch (DynamoDbException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
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
