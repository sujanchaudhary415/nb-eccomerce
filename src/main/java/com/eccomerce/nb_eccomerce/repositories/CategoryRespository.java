package com.eccomerce.nb_eccomerce.repositories;

import com.eccomerce.nb_eccomerce.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRespository extends JpaRepository<Category,Long> {
}
