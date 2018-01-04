package com.github.schmittjoaopedro.repository;

import com.github.schmittjoaopedro.domain.ObjectValue;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface ObjectValueRepository extends Neo4jRepository<ObjectValue, Long> {
}
