package com.example.dynamo_sdl.config;

import io.awspring.cloud.dynamodb.DynamoDbTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.http.urlconnection.UrlConnectionHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.net.URI;

@Configuration
public class DynamoConfig {

    //    @Bean
    /*public DynamoDbClient config() {
        return DynamoDbClient.builder()
                .httpClientBuilder(UrlConnectionHttpClient.builder()).endpointOverride(URI.create("http://localhost:8000"))
                .region(Region.US_EAST_1)
                .build();

    }*/

    // AI Version
    @Bean
    public DynamoDbClient dynamoDbClient() {
        return DynamoDbClient.builder()
                .httpClientBuilder(UrlConnectionHttpClient.builder())
//                .endpointOverride(URI.create("http://localhost:8000"))
                .region(Region.US_EAST_1) // You can choose any valid region
                .build();
    }

//    @Bean
//    public DynamoDbEnhancedClient dynamoDbEnhancedClient(DynamoDbClient dynamoDbClient) {
//        return DynamoDbEnhancedClient.builder().dynamoDbClient(dynamoDbClient).build();
//    }
//
//    @Bean
//    public DynamoDbTemplate dynamoDbTemplate(DynamoDbEnhancedClient dynamoDbEnhancedClient) {
//        return new DynamoDbTemplate(dynamoDbEnhancedClient);
//    }


    @Bean
    public DynamoDbEnhancedClient dynamoDbEnhancedClient() {
        return DynamoDbEnhancedClient.builder().dynamoDbClient(dynamoDbClient()).build();
    }

    @Bean
    public DynamoDbTemplate dynamoDbTemplate() {
        return new DynamoDbTemplate(dynamoDbEnhancedClient());
    }


}
