package com.example.dynamo_sdl.entiry;

import lombok.Data;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

import java.util.Map;

@DynamoDbBean
@Data
public class Customers {
    private String customerId;
    private String date;
    private Map<String, Object> jsonData;

    public Customers() {
    }

    public Customers(String customerId, String date, Map<String, Object> jsonData) {
        this.customerId = customerId;
        this.date = date;
        this.jsonData = jsonData;
    }

    public Customers(String customerId, String date) {
        this.customerId = customerId;
        this.date = date;
    }

    @DynamoDbPartitionKey
    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    @DynamoDbSortKey
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @DynamoDbAttribute("jsonData")
    public Map<String, Object> getJsonData() {
        return jsonData;
    }

    public void setJsonData(Map<String, Object> jsonData) {
        this.jsonData = jsonData;
    }
}