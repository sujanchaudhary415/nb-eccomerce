package com.eccomerce.nb_eccomerce;

import com.eccomerce.nb_eccomerce.entity.Role;
import com.eccomerce.nb_eccomerce.entity.User;
import com.eccomerce.nb_eccomerce.enums.AppRole;
import com.eccomerce.nb_eccomerce.repository.RoleRepository;
import com.eccomerce.nb_eccomerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Order(2)
@Component
@RequiredArgsConstructor
public class AdminSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${spring.app.username}")
    private String adminUsername;

    @Value("${spring.app.email}")
    private String adminEmail;

    @Value("${spring.app.password}")
    private String adminPassword;

    @Override
    public void run(String... args) {

        if (userRepository.existsByUsername(adminUsername)) {
            return;
        }

        Role adminRole = roleRepository
                .findByRoleName(AppRole.ROLE_ADMIN)
                .orElseThrow(() ->
                        new RuntimeException("ROLE_ADMIN not found")
                );

        User admin = new User();

        admin.setUsername(adminUsername);
        admin.setEmail(adminEmail);
        admin.setPassword(
                passwordEncoder.encode(adminPassword)
        );
        admin.setRoles(Set.of(adminRole));

        userRepository.save(admin);
    }
}
