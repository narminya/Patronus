package com.demo.patronus.services;

import com.demo.patronus.models.jpa.User;

import java.util.UUID;

public interface UserService {

    User getUser(String username);
    User getUserById(UUID  userId);
}
