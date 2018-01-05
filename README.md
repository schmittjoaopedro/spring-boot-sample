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

```xml
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

![Package organization](package_organization.png)

Uma breve descrição os pacotes:
* src/main/java - contém todo o código Java da aplicação
* src/main/resources/static - contém os arquivos estáticos de js, css e imagens.
* src/main/resources/templates - contém os arquivos html que são acessados por URI mapeadas no servidor
* src/test - contém todos os arquivos Java e auxiliares usados para testar a aplicação

## Código fonte

O arquivo *App.java* é a classe principal reponsável por iniciar a aplicação WEB.

App.java 
```java
package com.github.schmittjoaopedro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.session.SessionAutoConfiguration;

// A convenience annotation used to configure the application 
@SpringBootApplication
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

}
```

### Pacote *configuration*

É apresentado na listagem abaixo a configuração da base de dados com Spring Data Neo4J (SDN).

PersistenceContextConfig.java
```java
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
    private Environment env; // Receive the properties from "application.properties" file
    
    @Bean
    public SessionFactory getSessionFactory() {
        SessionFactory sessionFactory = new SessionFactory(configuration(), "com.github.schmittjoaopedro.domain");
        return sessionFactory;
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
        return new org.neo4j.ogm.config.Configuration.Builder()
            .uri(uri)
            .credentials(username, password)
            .build();
    }

}
```

A parte de autenticação e autorização apresentada na listagem abaixo, contém três formas de autenticação diferente, usando o Active Directory (AD), em memória e usando a base de dados.
Referente a autorização, é permitido aos usuários anônimos acessar a página de login, cadastro, arquivos estáticos (assets) e serviços REST da API. 

WebSecurityConfig.java
```java
package com.github.schmittjoaopedro.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.ldap.authentication.ad.ActiveDirectoryLdapAuthenticationProvider;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.annotation.Resource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String DOMAIN = "com.example";

    private static final String URL = "ldap://ad_app.example.com:389";

    private static final String ROOT_DN = "DC=example,DC=com";

    @Resource
    private UserDetailsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        // Use this for AD
        //auth.authenticationProvider(activeDirectoryLdapAuthenticationProvider());

        //Memory authentication
        //auth.inMemoryAuthentication()
        //        .passwordEncoder(NoOpPasswordEncoder.getInstance())
        //        .withUser("user")
        //        .password("pass")
        //        .roles("USER");

        //Database authentication
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf()
            .disable()
        .authorizeRequests()
            .antMatchers("/assets/**", "/api/**", "/login", "/login.jsp", "/register", "/register.jsp", "/css/**", "/js/**", "/images/**", "/**/favicon.ico").permitAll()
            .anyRequest().authenticated()
            .and()
        .formLogin()
            .loginPage("/login")
            .successHandler(successHandler())
            .permitAll()
            .and()
        .logout()   
            .permitAll()
            .and()
        .httpBasic();
    }

    @Bean // A provider used to connect in an AD server
    public AuthenticationProvider activeDirectoryLdapAuthenticationProvider() {
        ActiveDirectoryLdapAuthenticationProvider authenticationProvider = new ActiveDirectoryLdapAuthenticationProvider(DOMAIN, URL, ROOT_DN);
        authenticationProvider.setConvertSubErrorCodesToExceptions(true);
        authenticationProvider.setUseAuthenticationRequestCredentials(true);
        return authenticationProvider;
    }

    @Bean // Default redirect after user authentication
    public AuthenticationSuccessHandler successHandler() {
        SimpleUrlAuthenticationSuccessHandler handler = new SimpleUrlAuthenticationSuccessHandler();
        handler.setDefaultTargetUrl("/");
        return handler;
    }

}
```

### Pacote *controller*

O pacote controlador tem três tipos de resources, o primeiro tipo permite que usuário anônimos acessem serviços da API REST (ApiController), outro faz o mapeamento das páginas html (PagesController) e o último tipo permite que somente usuários autorizados possam acessar as requisições REST (RoleController e UserController).

ApiController.java
```java
package com.github.schmittjoaopedro.controller;

import com.github.schmittjoaopedro.domain.User;
import com.github.schmittjoaopedro.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/") // The /api is allowed to be used by anonymous users
public class ApiController {

    @Resource
    private UserService userService;

    @PostMapping("/register")
    public void createAccount(@RequestBody User user) {
        if(user.getId() == null)
            userService.createAccount(user);
    }

}

```
PagesController.java
```java
package com.github.schmittjoaopedro.controller;

import com.github.schmittjoaopedro.utils.SessionManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PagesController {

    @GetMapping("/")
    public String getHomePage(Model model) {
        model.addAttribute("user", SessionManager.getInstances().getSessionUser());
        model.addAttribute("module", "home");
        return "index";
    }

    @GetMapping("/users")
    public String getUsersPage(Model model) {
        model.addAttribute("user", SessionManager.getInstances().getSessionUser());
        model.addAttribute("module", "users");
        return "application/user-admin";
    }

    @GetMapping("/roles")
    public String getRolesPage(Model model) {
        model.addAttribute("user", SessionManager.getInstances().getSessionUser());
        model.addAttribute("module", "roles");
        return "application/role-admin";
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String getRegisterPage() {
        return "register";
    }

}
```
UserController.java
```java
package com.github.schmittjoaopedro.controller;

import com.github.schmittjoaopedro.domain.User;
import com.github.schmittjoaopedro.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/resources/users")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping
    public void create(@RequestBody User user) {
        userService.save(user);
    }

    @PutMapping
    public void update(@RequestBody User user) {
        userService.save(user);
    }

    @GetMapping
    public List<User> findAll() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public User findById(@PathVariable("id") Long id) {
        return userService.findById(id);
    }

}
```

### Pacote *domain*

O pacote domain contém todas as entidades Java que representam os objetos de negócio. 
Nesse caso as classes *User.java* e *Role.java* são usadas para segurança e as demais representam objetos de teste.
Todas as classes são mapeadas para serem persistidas no Neo4j usando o Spring Data Neo4j (SDN).
A seguir é apresentado um exemplo de classe.

Role.java
```java
package com.github.schmittjoaopedro.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@NodeEntity
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @JsonIgnore // To not generate cyclical referente by Jackson
    private Set<User> users = new HashSet<>();

    public Role() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

}
```

### Pacote *repository*

O pacote repository é reponsável por prover os métodos que irão executar a persistência dos objetos de domínio na base de dados.
Pelo SDN a definição de interfaces que extendem Neo4jRepository é suficiente para o Spring prover a implementação necessária, e quando é necessário algo mais específico as interfaces podem então ser implementadas.
Abaixo é apresentado um exemplo de interface.

RoleRepository:
```java
package com.github.schmittjoaopedro.repository;

import com.github.schmittjoaopedro.domain.Role;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface RoleRepository extends Neo4jRepository<Role, Long> {

    Role findByName(String name);

}
```

### Pacote *service*

Esse pacote é utilizado para preparar os objetos para persistência e realizar alguma lógica de negócio. 
Do ponto de vista técnico, a maior parte dos métodos também é responsável por abrir a transação com a base de dados.
A listagem abaixo apresenta uma classe service de exemplo.

RoleService.java
```java
package com.github.schmittjoaopedro.service;

import com.github.schmittjoaopedro.domain.Role;
import com.github.schmittjoaopedro.repository.RoleRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class RoleService {

    @Resource
    private RoleRepository roleRepository;

    public void saveRole(Role role) {
        roleRepository.save(role);
    }

    public List<Role> findAll() {
        List<Role> roles = new ArrayList<>();
        for(Role role : roleRepository.findAll()) {
            roles.add(role);
        }
        return roles;
    }

    public Role findRoleByName(String name) {
        return roleRepository.findByName(name);
    }

    public Role findRoleById(Long id) {
        return roleRepository.findById(id).get();
    }

}
```

### Pacote *utils*

Este último pacote possui algumas ferramentas que podem ser usadas a qualquer momento no código fonte.

A listagem abaixo apresenta a classe *ContextProvider*, que permite acesso aos beans gerenciados pelo spring sem configurar uma injeção de dependências.

ContextProvider.java
```java
package com.github.schmittjoaopedro.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ContextProvider implements ApplicationContextAware {

    private static ApplicationContext CONTEXT;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        CONTEXT = applicationContext;
    }

    public static <T> T getBean(Class<T> beanClass) {
        return CONTEXT.getBean(beanClass);
    }

    public static Object getBean(String beanName) {
        return CONTEXT.getBean(beanName);
    }

}
```

A classe *SessionManager* possui a implementação que permite recuperar as informações do usuário da sessão.

SessionManager.java
```java
package com.github.schmittjoaopedro.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class SessionManager {

    private static final SessionManager SESSION_MANAGER = new SessionManager();

    private SessionManager() {
        super();
    }

    public static SessionManager getInstances() {
        return SESSION_MANAGER;
    }

    public UserDetails getSessionUser() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            return principal instanceof UserDetails ? (UserDetails) principal : null;
        }
        return null;
    }

}
```

## Testes

A classe *FullPersistenceTest* foi configurada para realizar um teste do modelo de persistência. 
Esse teste, executa a criação e modificação de diferentes estruturas complexas. 
Foi percebido que a persistência do SDN não permitiu a eliminação de relacionamentos one-to-one ao definir o valor da propriedade do relacionamento do objeto sendo salvo como nulo, e também que a eliminação de objetos da coleção não permite a configuração do mecânismo de cascata.
A conclusão é que a persistência usando um formato stateless faz com que as implementações para gerenciar os relacionamentos das entidades fique mais complexo, quando comparado ao JPA usando Hibernate.
A listagem abaixo apresenta a classe de testes.

FullPersistenceTest.java
```java
package com.github.persistence;

import com.github.schmittjoaopedro.App;
import com.github.schmittjoaopedro.domain.*;
import com.github.schmittjoaopedro.repository.ObjectValueRepository;
import com.github.schmittjoaopedro.service.AttributeService;
import com.github.schmittjoaopedro.service.ClassTypeService;
import com.github.schmittjoaopedro.service.ObjectInstanceService;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = App.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FullPersistenceTest {

    @Resource
    private AttributeService attributeService;

    @Resource
    private ObjectInstanceService objectInstanceService;

    @Resource
    private ClassTypeService classTypeService;

    @Resource
    private ObjectValueRepository objectValueRepository;

    @Test
    public void _clearDatabase() {
        objectInstanceService.deleteAll();
        classTypeService.deleteAll();
        attributeService.deleteAll();
    }

    @Test
    public void createFullApplication() {

        Attribute C1 = new Attribute();
        C1.setName("CHARACTERISTIC_1");
        C1.setType(AttributeType.String);
        C1 = attributeService.save(C1);

        AttributeDescription C1DPT = new AttributeDescription();
        C1DPT.setLanguage("pt");
        C1DPT.setDescription("Característica Um");
        C1.getDescriptions().add(C1DPT);
        AttributeDescription C1EN = new AttributeDescription();
        C1EN.setLanguage("en");
        C1EN.setDescription("Attribute One");
        C1.getDescriptions().add(C1EN);
        C1 = attributeService.save(C1);

        C1.getDescriptions().stream().filter(item -> item.getLanguage().equals("pt")).findFirst().get().setDescription("Característica 1");
        C1.getDescriptions().stream().filter(item -> item.getLanguage().equals("en")).findFirst().get().setDescription("Attribute 1");
        C1 = attributeService.save(C1);

        AttributeValue C1V1 = new AttributeValue();
        C1V1.setAttribute(C1);
        C1V1.setValue("00001");
        C1.getValues().add(C1V1);
        AttributeValue C1V2 = new AttributeValue();
        C1V2.setAttribute(C1);
        C1V2.setValue("00002");
        AttributeValueDescription C1V2DPT = new AttributeValueDescription();
        C1V2DPT.setLanguage("pt");
        C1V2DPT.setDescription("Valor 00002");
        C1V2.getDescriptions().add(C1V2DPT);
        AttributeValueDescription C1V2DEN = new AttributeValueDescription();
        C1V2DEN.setLanguage("en");
        C1V2DEN.setDescription("Value 00002");
        C1V2.getDescriptions().add(C1V2DEN);
        C1.getValues().add(C1V2);
        C1 = attributeService.save(C1);

        C1.getValues().stream().filter(item -> item.getValue().equals("00002")).findFirst().get().getDescriptions().stream().filter(item -> item.getLanguage().equals("pt")).findFirst().get().setDescription("Valor 2");
        C1.getValues().stream().filter(item -> item.getValue().equals("00002")).findFirst().get().getDescriptions().stream().filter(item -> item.getLanguage().equals("en")).findFirst().get().setDescription("Value 2");
        C1 = attributeService.save(C1);

        ClassType OT1 = new ClassType();
        OT1.setName("OBJECT_TYPE_1");
        OT1.getAttributes().add(C1);
        OT1 = classTypeService.save(OT1);

        ObjectInstance O1 = new ObjectInstance();
        O1.setClassType(OT1);
        ObjectValue O1V1 = new ObjectValue();
        O1V1.setValue("00001");
        O1V1.setAttribute(C1);
        O1V1.setObjectInstance(O1);
        O1.getValues().add(O1V1);
        ObjectInstance O2 = new ObjectInstance();
        O2.setClassType(OT1);
        ObjectValue O2V1 = new ObjectValue();
        O2V1.setValue("00001");
        O2V1.setAttribute(C1);
        O2V1.setObjectInstance(O2);
        O2.getValues().add(O2V1);
        O1.getChildren().add(O2);
        ObjectInstance O3 = new ObjectInstance();
        O3.setClassType(OT1);
        ObjectValue O3V1 = new ObjectValue();
        O3V1.setValue("00001");
        O3V1.setAttribute(C1);
        O3V1.setObjectInstance(O3);
        O3.getValues().add(O3V1);
        O2.getChildren().add(O3);
        O1 = objectInstanceService.save(O1);

        // Remover associação direta. Problema ao remover o relacionamento por
        // meio do save! Tenho que fazer uma query manual
        ObjectValue OV = objectValueRepository.findById(O1.getValues().get(0).getId()).get();
        OV.setValue("00002");
        OV.setAttribute(null);
        objectValueRepository.save(OV);
        objectValueRepository.removeCharacteristic(OV.getId());

        // Remover o objeto da lista, não consigo remover sem mandar remover
        // explicitamente o objeto
        O1 = objectInstanceService.findOne(O1.getId());
        OV = O1.getValues().get(0);
        OV.setObjectInstance(null);
        O1.getValues().clear();
        objectInstanceService.save(O1);
        objectValueRepository.delete(OV);

        Assert.assertTrue(true);
    }

}

```

# Conclusão

A conclusão é que o desenvolvimento utilizando o spring-boot ficou extremamente simples e leve quando comparado aos formatos de desenvolvimento antigo.

A configuração da segurança e da persistência por meio de anotações faz com que a aplicação seja mais fácil de manter e tenha uma composição mais limpa.

As dificuldades encontradas ao utilizar o SDN comparado ao JPA não inviabilizam seu uso, pelo motivo de que o domínio da aplicação deveria ser modelado para uma base relacional.