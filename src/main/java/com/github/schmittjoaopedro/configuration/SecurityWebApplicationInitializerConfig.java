package com.github.schmittjoaopedro.configuration;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

public class SecurityWebApplicationInitializerConfig extends AbstractSecurityWebApplicationInitializer {

    public SecurityWebApplicationInitializerConfig() {
        super(WebSecurityConfig.class);
    }

}
