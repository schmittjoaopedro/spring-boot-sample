package com.github.schmittjoaopedro.service;

import com.github.schmittjoaopedro.domain.User;

public interface UserService {

    void save(User user);

    User findByEmail(String email);

}