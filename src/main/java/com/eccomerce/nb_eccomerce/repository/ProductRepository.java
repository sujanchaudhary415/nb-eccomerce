package com.eccomerce.nb_eccomerce.repository;

import com.eccomerce.nb_eccomerce.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
    boolean existsByProductNameIgnoreCase(String name);
    boolean existsByProductNameIgnoreCaseAndProductIdNot(String name,Long productId);
}
