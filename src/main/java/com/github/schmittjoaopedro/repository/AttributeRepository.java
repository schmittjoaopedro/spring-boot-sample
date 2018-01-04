package com.github.schmittjoaopedro.repository;


import com.github.schmittjoaopedro.domain.Attribute;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface AttributeRepository extends Neo4jRepository<Attribute, Long> {

}
