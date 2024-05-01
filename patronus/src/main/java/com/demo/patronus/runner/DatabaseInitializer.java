package com.demo.patronus.runner;

import com.demo.patronus.enums.ERole;
import com.demo.patronus.models.jpa.Role;
import com.demo.patronus.models.jpa.User;
import com.demo.patronus.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class DatabaseInitializer implements CommandLineRunner {

  private final RoleRepository roleRepository;
    @Override
    public void run(String... args) {
        if (roleRepository.findAll().isEmpty()) {
            roleRepository.saveAll(ROLES);
        }

        log.info("Database initialized");
    }

    private static final List<Role> ROLES = Arrays.asList(
            Role.builder().name(ERole.valueOf("USER")).build(),
            Role.builder().name(ERole.valueOf("ADMIN")).build()
    );

    private static final List<User> USERS = Arrays.asList(
            User.builder().build()
    );
}
