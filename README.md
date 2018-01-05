# Avaliação simples do Spring-boot

O objetivo desse projeto é, fazer uma avaliação superficial do spring-boot e algumas outras tecnologias referente há alguns requisitos comuns que a maioria das aplicações possuem.
A contribuição é compartilhar uma arquitetura inicial para a concepção de novos projetos.
As tecnologias avaliadas nesse projeto são as seguintes:
* Spring boot 2.0.0.M7
* Redis
* Neo4J

Os requisitos não funcionais comuns são:
* Um usuário sem autenticação só pode acessar a pagina de login e de cadastro.
* Um usuário autenticado pode acessar as páginas de administração.

Os requisitos funcionais são:
* Ao reiniciar o servidor, o usuário não pode perder a sessão
* Persistência em cascata, possibilitando dados stateless.

# Ambiente

Em relação ao ambiente de desenvolvimento, os seguintes programas foram utilizados:
 * JDK versão 9.0.1+11 64bit
 * IntelliJ IDEA Community 2017.1.4
 * Maven 3.3.9
 * Docker 17.09.1-ce
 * Redis 4.0.6 64bits
 * Neo4j 3.3.1

Para preparar o ambiente de desenvolvimento é necessário seguir os seguintes passos:
 * Importar o projeto do Github no IntelliJ
 * Iniciar o redis com o comando `docker run redis`
 * Iniciar o Neo4J com o comando `docker run --publish=7474:7474 --publish=7687:7687 --volume=$HOME/neo4j/data:/data neo4j`

# Código fonte

A seguir será apresentado a organização do código fonte:

## pom.xml

```{xml}
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">

    <!-- Project header -->
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.github.schmittjoaopedro</groupId>
    <artifactId>spring-boot-sample</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <packaging>jar</packaging>
    <name>spring-boot-sample</name>
    <description>Demo project for Spring Boot</description>

    <!-- Spring boot configuration -->
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.0.0.M7</version>
    </parent>

    <!-- Source code and compiler properties -->
    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
        <java.version>1.8</java.version>
    </properties>

    <dependencies>
        <!-- TEST -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <!-- DATA -->
        <dependency>
            <groupId>org.springframework.data</groupId>
            <artifactId>spring-data-neo4j</artifactId>
        </dependency>
        <dependency>
            <groupId>org.neo4j</groupId>
            <artifactId>neo4j-ogm-bolt-driver</artifactId>
            <version>${neo4j-ogm.version}</version>
        </dependency>
        <!-- WEB -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-thymeleaf</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-redis</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.session</groupId>
            <artifactId>spring-session-data-redis</artifactId>
        </dependency>
        <!-- SECURITY -->
        <dependency>
            <groupId>org.springframework.security</groupId>
            <artifactId>spring-security-config</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.security</groupId>
            <artifactId>spring-security-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.security</groupId>
            <artifactId>spring-security-core</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.security</groupId>
            <artifactId>spring-security-ldap</artifactId>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-pmd-plugin</artifactId>
                <version>3.8</version>
            </plugin>
        </plugins>
    </build>

    <!-- Additional repositories to use spring-boot milestones version 2 -->
    <repositories>
        <repository>
            <id>spring-snapshots</id>
            <url>http://repo.spring.io/snapshot</url>
            <snapshots><enabled>true</enabled></snapshots>
        </repository>
        <repository>
            <id>spring-milestones</id>
            <url>http://repo.spring.io/milestone</url>
        </repository>
    </repositories>
    <pluginRepositories>
        <pluginRepository>
            <id>spring-snapshots</id>
            <url>http://repo.spring.io/snapshot</url>
        </pluginRepository>
        <pluginRepository>
            <id>spring-milestones</id>
            <url>http://repo.spring.io/milestone</url>
        </pluginRepository>
    </pluginRepositories>

</project>
```

## Estrutura de pacotes

O projeto está organizado conforme a seguinte figura:

![alt text](package_organization.png "Package organization" { width=50% })

Uma breve descrição os pacotes:
* src/main/java - contém todo o código Java da aplicação
* src/main/resources/static - contém os arquivos estáticos de js, css e imagens.
* src/main/resources/templates - contém os arquivos html que são acessados por URI mapeadas no servidor
* src/test - contém todos os arquivos Java e auxiliares usados para testar a aplicação

## Código fonte

O arquivo *App.java* é a classe principal reponsável por iniciar a aplicação WEB.

```{java}
package com.github.schmittjoaopedro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.session.SessionAutoConfiguration;

@SpringBootApplication
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

}
```

estrutura de pacotes
    configuration
    controller
    domain
    repository
    service
    utils
    test
resources
    static e public
    application.properties
