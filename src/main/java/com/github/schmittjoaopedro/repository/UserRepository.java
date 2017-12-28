package com.github.schmittjoaopedro.repository;

import com.github.schmittjoaopedro.domain.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface UserRepository extends Neo4jRepository<User, Long> {

    User findByEmail(String email);

}
