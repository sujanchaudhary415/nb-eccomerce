package com.eccomerce.nb_eccomerce.service;

import com.eccomerce.nb_eccomerce.entity.Category;

import java.util.List;

public interface CategoryService {
    List<Category>getAllCategories();
    void createCategory(Category category);
    String deleteCategory(Long categoryId);
}
