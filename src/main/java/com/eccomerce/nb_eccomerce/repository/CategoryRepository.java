package com.eccomerce.nb_eccomerce.repository;

import com.eccomerce.nb_eccomerce.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface CategoryRepository extends JpaRepository<Category,Long> {
    boolean existsByCategoryNameIgnoreCase(String categoryName);
    boolean existsByCategoryNameIgnoreCaseAndCategoryIdNot(String name,Long categoryId);
}
