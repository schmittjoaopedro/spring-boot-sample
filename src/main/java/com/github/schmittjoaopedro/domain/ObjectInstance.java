package com.github.schmittjoaopedro.domain;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

import java.util.ArrayList;
import java.util.List;

@NodeEntity
public class ObjectInstance {

    @Id
    @GeneratedValue
    private Long id;

    private ClassType classType;

    private List<ObjectInstance> children;

    private List<ObjectValue> values;

    public ObjectInstance() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ClassType getClassType() {
        return classType;
    }

    public void setClassType(ClassType classType) {
        this.classType = classType;
    }

    public List<ObjectInstance> getChildren() {
        if(children == null) children = new ArrayList<>();
        return children;
    }

    public void setChildren(List<ObjectInstance> children) {
        this.children = children;
    }

    public List<ObjectValue> getValues() {
        if(values == null) values = new ArrayList<>();
        return values;
    }

    public void setValues(List<ObjectValue> values) {
        this.values = values;
    }
}
