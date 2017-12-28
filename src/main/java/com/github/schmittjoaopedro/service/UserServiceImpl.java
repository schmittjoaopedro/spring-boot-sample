package com.github.schmittjoaopedro.service;

import com.github.schmittjoaopedro.domain.Role;
import com.github.schmittjoaopedro.domain.User;
import com.github.schmittjoaopedro.repository.RoleRepository;
import com.github.schmittjoaopedro.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserRepository userRepository;

    @Resource
    private RoleRepository roleRepository;

    @Resource
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void save(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(new HashSet<>());
        for(Role role : roleRepository.findAll()) {
            user.getRoles().add(role);
        }
        userRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

}
