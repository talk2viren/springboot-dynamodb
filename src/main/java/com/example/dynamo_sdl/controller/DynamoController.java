package com.example.dynamo_sdl.controller;

import com.example.dynamo_sdl.entiry.Employee;
import io.awspring.cloud.dynamodb.DynamoDbTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class DynamoController {

    @Autowired
    private DynamoDbTemplate template;

    @Autowired
    private DynamoDbClient dynamoDbClient;

    @GetMapping("/names")
    public ResponseEntity<?> getAll(){
        System.out.println(template);
        Employee employee =new Employee("empId","virendra","yadav");
//        template.scanAll("");

//        ListTablesRequest request = ListTablesRequest.builder().build();
//        ListTablesResponse listTablesResponse = dynamoDbClient.listTables(request);
//        listTablesResponse.tableNames().stream().forEach(System.out::println);

        if(dynamoDbClient.listTables().tableNames()
                .stream()
                .anyMatch(x -> x.equals("Employee"))){
            template.save(employee);
        }else {
            System.out.println("Table not exists ..");
        }

        return ResponseEntity.ok(List.of("name1","name2"));
    }


}
