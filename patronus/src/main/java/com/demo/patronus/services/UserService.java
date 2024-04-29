package com.demo.patronus.services;

import com.demo.patronus.models.Block;
import com.demo.patronus.models.Follow;
import com.demo.patronus.models.User;

import java.util.List;
import java.util.UUID;

public interface UserService {

    User getUser(String username);
    User getUserById(UUID  userId);
}
