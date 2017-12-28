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
@ComponentScan("com.github.schmittjoaopedro")
@EnableTransactionManagement
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
        String username = env.getProperty("spring.data.neo4j.username");
        String password = env.getProperty("spring.data.neo4j.password");
        String uri = env.getProperty("spring.data.neo4j.uri");
        org.neo4j.ogm.config.Configuration configuration = new org.neo4j.ogm.config.Configuration();
        configuration.driverConfiguration().setCredentials(username, password);
        configuration.driverConfiguration().setDriverClassName("org.neo4j.ogm.drivers.bolt.driver.BoltDriver");
        configuration.driverConfiguration().setURI(uri);
        return configuration;
    }

}
