package com.eccomerce.nb_eccomerce.repository;

import com.eccomerce.nb_eccomerce.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order,Long> {
    Optional<Order> findByOrderIdAndUserUserId(Long orderId, Long userId);
    List<Order> findByUserUserIdOrderByCreatedAtDesc(Long userId);
}
