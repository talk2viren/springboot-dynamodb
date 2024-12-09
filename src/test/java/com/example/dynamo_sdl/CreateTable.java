package com.example.dynamo_sdl;

import org.junit.jupiter.api.Test;
import software.amazon.awssdk.services.dynamodb.model.AttributeDefinition;
import software.amazon.awssdk.services.dynamodb.model.CreateTableRequest;
import software.amazon.awssdk.services.dynamodb.model.KeySchemaElement;
import software.amazon.awssdk.services.dynamodb.model.ScalarAttributeType;

public class CreateTable {

    @Test
    public void test_1(){
//        CreateTableRequest request = new CreateTableRequest()
//                .withAttributeDefinitions(new AttributeDefinition(
//                        "Name", ScalarAttributeType.S))
//                .withKeySchema(new KeySchemaElement("Name", KeyType.HASH))
//                .withProvisionedThroughput(new ProvisionedThroughput(
//                        new Long(10), new Long(10)))
//                .withTableName(table_name);
//
//        final AmazonDynamoDB ddb = AmazonDynamoDBClientBuilder.defaultClient();
//
//        try {
//            CreateTableResult result = ddb.createTable(request);
//            System.out.println(result.getTableDescription().getTableName());
//        } catch (AmazonServiceException e) {
//            System.err.println(e.getErrorMessage());
//            System.exit(1);
//        }
    }
}
