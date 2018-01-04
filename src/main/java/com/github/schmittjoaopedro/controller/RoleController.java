package com.github.schmittjoaopedro.controller;

import com.github.schmittjoaopedro.domain.Role;
import com.github.schmittjoaopedro.service.RoleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/resources/roles")
public class RoleController {

    @Resource
    private RoleService roleService;

    @PostMapping
    public void create(@RequestBody Role role) {
        roleService.saveRole(role);
    }

    @PutMapping
    public void update(@RequestBody Role role) {
        roleService.saveRole(role);
    }

    @GetMapping
    public List<Role> findAll() {
        return roleService.findAll();
    }

    @GetMapping("/{id}")
    public Role findById(@PathVariable("id") Long id) {
        return roleService.findRoleById(id);
    }

}
