package com.github.schmittjoaopedro.configuration;

import org.neo4j.ogm.session.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.transaction.Neo4jTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;

@Configuration
@EnableTransactionManagement
@ComponentScan("com.github.schmittjoaopedro")
@EnableNeo4jRepositories("com.github.schmittjoaopedro.repository")
@PropertySource("classpath:application.properties")
public class PersistenceContextConfig {

    @Resource
    private Environment env;

    @Bean
    public SessionFactory getSessionFactory() {
        return new SessionFactory(configuration(), "com.github.schmittjoaopedro.domain");
    }

    @Bean
    public Neo4jTransactionManager transactionManager() throws Exception {
        return new Neo4jTransactionManager(getSessionFactory());
    }

    @Bean
    public org.neo4j.ogm.config.Configuration configuration() {
        return new org.neo4j.ogm.config.Configuration.Builder()
            .uri(env.getProperty("spring.data.neo4j.uri"))
            .credentials(env.getProperty("spring.data.neo4j.username"), env.getProperty("spring.data.neo4j.password"))
            .build();
    }

}
