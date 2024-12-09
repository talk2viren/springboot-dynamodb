package com.example.dynamo_sdl.util;

import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;
import software.amazon.awssdk.services.dynamodb.model.QueryRequest;
import software.amazon.awssdk.services.dynamodb.model.QueryResponse;

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

}
