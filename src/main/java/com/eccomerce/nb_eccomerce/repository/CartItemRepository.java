package com.eccomerce.nb_eccomerce.repository;

import com.eccomerce.nb_eccomerce.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {
    Optional<CartItem> findByCartCartIdAndProductProductId(Long cartId,Long productId);
    Optional<CartItem> findByCartCartIdAndCartItemId(Long cartId,Long cartItemId);
    void deleteByProductProductId(Long productId);
}
