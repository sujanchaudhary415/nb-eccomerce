package com.eccomerce.nb_eccomerce.repository;

import com.eccomerce.nb_eccomerce.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product,Long> {
    boolean existsByProductNameIgnoreCase(String name);
    boolean existsByProductNameIgnoreCaseAndProductIdNot(String name,Long productId);
    Page<Product> findByCategory_CategoryId(Long categoryId, Pageable pageable);

    @Query(value = """
            SELECT EXISTS (
                SELECT 1
                FROM products
                WHERE category_id = :categoryId
            )
            """, nativeQuery = true)
    boolean existsAnyProductByCategoryId(@Param("categoryId") Long categoryId);
}
