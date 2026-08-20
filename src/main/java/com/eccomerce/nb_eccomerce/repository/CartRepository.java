package com.eccomerce.nb_eccomerce.repository;

import com.eccomerce.nb_eccomerce.entity.Cart;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart,Long> {
    Optional<Cart> findByUserUserId(Long userId);

}
