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
