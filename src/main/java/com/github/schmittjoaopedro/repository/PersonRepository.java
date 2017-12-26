package com.github.schmittjoaopedro.repository;

import com.github.schmittjoaopedro.domain.Person;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends Neo4jRepository<Person, Long> {

    Person findByFirstNameAndAge(String firstName);

}
