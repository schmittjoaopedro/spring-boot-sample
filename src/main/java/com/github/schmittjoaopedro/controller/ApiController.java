package com.github.schmittjoaopedro.controller;

import com.github.schmittjoaopedro.domain.User;
import com.github.schmittjoaopedro.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/")
public class ApiController {

    @Resource
    private UserService userService;

    @PostMapping("/register")
    public void createAccount(@RequestBody User user) {
        if(user.getId() == null)
            userService.save(user);
    }

}
