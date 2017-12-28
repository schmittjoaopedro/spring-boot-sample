package com.github.schmittjoaopedro.service;

import com.github.schmittjoaopedro.domain.User;
import com.github.schmittjoaopedro.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class UserService {

    @Resource
    private UserRepository userRepository;

    @Resource
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public void createAccount(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(new HashSet<>());
        userRepository.save(user);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User findById(Long id) {
        return userRepository.findOne(id);
    }

    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        for(User user : userRepository.findAll()) {
            users.add(user);
        }
        return users;
    }

    public void save(User user) {
        User persistedUsers = userRepository.findOne(user.getId());
        boolean isNewPassword = !persistedUsers.getPassword().equals(user.getPassword());
        if(isNewPassword) {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        }
        userRepository.save(user);
    }

}
