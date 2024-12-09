package com.example.dynamo_sdl.entiry;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.Map;

@DynamoDbBean
public class GenericItem {
    public GenericItem() {
    }

    private Map<String, AttributeValue> attributes;

    public GenericItem(Map<String, AttributeValue> attributes) {
        this.attributes = attributes;
    }

    public Map<String, AttributeValue> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, AttributeValue> attributes) {
        this.attributes = attributes;
    }
}
