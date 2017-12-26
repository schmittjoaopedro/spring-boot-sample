package com.github.schmittjoaopedro.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public")
public class PublicController {

    @RequestMapping("/persons")
    public @ResponseBody String publicHome() {
        return "Public data!";
    }

}
