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
