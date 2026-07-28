package com.eccomerce.nb_eccomerce.repository;

import com.eccomerce.nb_eccomerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);



    Optional<User> findByUsername(String username);
}
