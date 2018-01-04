package com.github.schmittjoaopedro.repository;

import com.github.schmittjoaopedro.domain.ClassType;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface ClassTypeRepository extends Neo4jRepository<ClassType, Long> {
}
