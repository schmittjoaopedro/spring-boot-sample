package com.github.schmittjoaopedro.domain;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

import java.util.ArrayList;
import java.util.List;

@NodeEntity
public class AttributeValue {

    @Id
    @GeneratedValue
    private Long id;

    private Attribute attribute;

    private String value;

    private List<AttributeValueDescription> descriptions;

    public AttributeValue() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Attribute getAttribute() {
        return attribute;
    }

    public void setAttribute(Attribute attribute) {
        this.attribute = attribute;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<AttributeValueDescription> getDescriptions() {
        if(descriptions == null) descriptions = new ArrayList<>();
        return descriptions;
    }

    public void setDescriptions(List<AttributeValueDescription> descriptions) {
        this.descriptions = descriptions;
    }
}
