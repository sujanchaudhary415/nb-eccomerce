package com.eccomerce.nb_eccomerce.repository;

import com.eccomerce.nb_eccomerce.entity.Product;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product,Long> {
    boolean existsByProductNameIgnoreCase(String name);
    boolean existsByProductNameIgnoreCaseAndProductIdNot(String name,Long productId);

    @EntityGraph(attributePaths = "category")
    Page<Product> findByCategory_CategoryId(Long categoryId, Pageable pageable);
    @EntityGraph(attributePaths = "category")
    Page<Product> findAll(Pageable pageable);

    @Query(value = """
            SELECT EXISTS (
                SELECT 1
                FROM products
                WHERE category_id = :categoryId
            )
            """, nativeQuery = true)
    boolean existsAnyProductByCategoryId(@Param("categoryId") Long categoryId);


    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
            SELECT p
            FROM Product p
            WHERE p.productId = :productId
            """)
    Optional<Product> findByIdForUpdate(
            @Param("productId") Long productId
    );
}
