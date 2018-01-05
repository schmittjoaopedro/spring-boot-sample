package com.github.schmittjoaopedro.repository;

import com.github.schmittjoaopedro.domain.ObjectValue;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface ObjectValueRepository extends Neo4jRepository<ObjectValue, Long> {

    @Query("MATCH (oval:ObjectValue)-[oval_attr:ATTRIBUTE]->(attr:Attribute) WHERE ID(oval) = {0} DELETE oval_attr")
    void removeCharacteristic(Long objectValueId);

}
