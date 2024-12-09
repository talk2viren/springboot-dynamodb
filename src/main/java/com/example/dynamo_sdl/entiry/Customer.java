package com.example.dynamo_sdl.entiry;

import lombok.Data;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

@DynamoDbBean
@Data
public class Customer {
    private String customerId;
    private String date;

    public Customer() {
    }

    public Customer(String customerId, String date) {
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
}