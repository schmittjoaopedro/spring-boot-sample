package com.github.schmittjoaopedro.domain;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.ArrayList;
import java.util.List;

@NodeEntity
public class Attribute {

    @GraphId
    private Long id;

    private String name;

    private AttributeType type;

    private List<AttributeDescription> descriptions;

    private List<AttributeValue> values;

    public Attribute() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AttributeType getType() {
        return type;
    }

    public void setType(AttributeType type) {
        this.type = type;
    }

    public List<AttributeDescription> getDescriptions() {
        if(descriptions == null) descriptions = new ArrayList<>();
        return descriptions;
    }

    public void setDescriptions(List<AttributeDescription> descriptions) {
        this.descriptions = descriptions;
    }

    public List<AttributeValue> getValues() {
        if(values == null) values = new ArrayList<>();
        return values;
    }

    public void setValues(List<AttributeValue> values) {
        this.values = values;
    }
}
