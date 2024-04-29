package com.demo.patronus.services.impl;

import com.demo.patronus.exception.UserNotFoundException;
import com.demo.patronus.models.Block;
import com.demo.patronus.models.Follow;
import com.demo.patronus.models.User;
import com.demo.patronus.repository.BlockRepository;
import com.demo.patronus.repository.FollowRepository;
import com.demo.patronus.repository.UserRepository;
import com.demo.patronus.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    @Override
    public User getUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

    }

    @Override
    public User getUserById(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }



}
