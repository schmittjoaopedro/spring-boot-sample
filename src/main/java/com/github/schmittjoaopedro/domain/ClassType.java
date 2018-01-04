package com.github.schmittjoaopedro.domain;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

import java.util.ArrayList;
import java.util.List;

@NodeEntity
public class ClassType {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private List<Attribute> attributes;

    public ClassType() {
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

    public List<Attribute> getAttributes() {
        if(attributes == null) attributes = new ArrayList<>();
        return attributes;
    }

    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }
}
