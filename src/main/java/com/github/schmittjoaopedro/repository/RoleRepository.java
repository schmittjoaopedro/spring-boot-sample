package com.github.schmittjoaopedro.repository;

import com.github.schmittjoaopedro.domain.Role;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface RoleRepository extends Neo4jRepository<Role, Long> {
}
