package com.example.dynamo_sdl;

import com.example.dynamo_sdl.entiry.MovieDetails;
import io.awspring.cloud.dynamodb.DynamoDbTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.CreateTableEnhancedRequest;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@SpringBootTest
public class MovieDetailsTest {

    @Autowired
    private DynamoDbTemplate dynamoDbTemplate;

    @Autowired
    private DynamoDbClient dynamoDbClient;

    @Autowired
    private DynamoDbEnhancedClient dynamoDbEnhancedClient;

    @Test
    public void test_1() {
        dynamoDbClient.listTables().tableNames()
                .stream()
                .forEach(System.out::println);
    }


    @Test
    public void test_2() {
        DynamoDbEnhancedClient enhancedClient = DynamoDbEnhancedClient.builder().dynamoDbClient(dynamoDbClient).build();
        Util.createTable(enhancedClient);
    }

    // DynamoDB Template : Create Record
    @Test
    void test_3() {
//        MovieDetails movieDetails=new MovieDetails();
        MovieDetails movieDetails = new MovieDetails("two2", "two", "three", "four", "five", "six", "siz");
        dynamoDbTemplate.save(movieDetails);
    }

    // DynamoDB Template : Read Record
    @Test
    void test_4() {
        dynamoDbTemplate.scanAll(MovieDetails.class).iterator().next().items().stream().forEach(System.out::println);
    }

    private static class Util {
        private static void createTable(DynamoDbEnhancedClient enhancedClient) {
            DynamoDbTable<MovieDetails> movieDetails = enhancedClient.table("MovieDetails", TableSchema.fromBean(MovieDetails.class)); // Create the table
            movieDetails.createTable(CreateTableEnhancedRequest.builder().build());
            System.out.println("Table created successfully.");
        }
    }

}