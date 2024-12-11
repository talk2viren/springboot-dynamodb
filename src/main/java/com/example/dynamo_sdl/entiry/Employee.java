package com.example.dynamo_sdl.entiry;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

@DynamoDbBean

//@Data
public class Employee {

    private String loginAlias;
    private String name;
    private String lastName;

    public Employee() {
    }

    public Employee(String loginAlias, String name, String lastName) {
        this.loginAlias = loginAlias;
        this.name = name;
        this.lastName = lastName;
    }


    @Override
    public String toString() {
        return "Employee{" +
                "loginAlias='" + loginAlias + '\'' +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }

    @DynamoDbPartitionKey
    public String getLoginAlias(){
        return loginAlias;
    }

    public void setLoginAlias(String loginAlias) {
        this.loginAlias = loginAlias;
    }

    @DynamoDbSortKey
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
