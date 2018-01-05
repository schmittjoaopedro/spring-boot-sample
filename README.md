# Spring boot simple application

The objective of this project is to test spring boot with some other technologies to validate the functionalities and difficulties. The technologies utilized are:
* Spring boot 2.0.0.M7
* Redis
* Neo4J

It's project contains the following requirements:
* An unauthenticated user has only access to login and register pages
* An authenticated user has access to the users and roles admin page
* The Redis is used to keep the session (when the server is restarted the user does not lose the session)
* The data is persisted at Neo4J to test the Spring Data API functionalities

The following will describe how the application is structured and how each component contribute to address all requirements.

## Project Dependencies

To start, this project was created using Maven. 
This project is transformed in a spring-boot application with the tag parent especifing to use the version 2.0.0.M7.
To use the new version of spring-boot 2 not released yet, is necessary add some additional repositories, declared at the end of the file.
The dependencies are organized by the following categories:
* TEST - allow to create unit and integration tests using spring-boot.
* DATA - contains the necessary dependencies to persist data at Neo4J
* WEB - contains dependencies to map the HTML pages URI and to manage the http session with Redis.
* SECURITY - contains some different type of authentication and authorization implementations.

## Project Packages