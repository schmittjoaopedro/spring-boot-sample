package com.github.schmittjoaopedro.domain;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
public class ObjectValue {

    @Id
    @GeneratedValue
    private Long id;

    private Attribute attribute;

    private ObjectInstance objectInstance;

    private String value;

    public ObjectValue() {
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

    public ObjectInstance getObjectInstance() {
        return objectInstance;
    }

    public void setObjectInstance(ObjectInstance objectInstance) {
        this.objectInstance = objectInstance;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
