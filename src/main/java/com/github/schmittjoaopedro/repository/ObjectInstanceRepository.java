package com.github.schmittjoaopedro.repository;

import com.github.schmittjoaopedro.domain.ObjectInstance;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface ObjectInstanceRepository extends Neo4jRepository<ObjectInstance, Long> {
}
