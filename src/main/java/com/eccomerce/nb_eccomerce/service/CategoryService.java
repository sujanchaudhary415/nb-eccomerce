package com.eccomerce.nb_eccomerce.service;

import com.eccomerce.nb_eccomerce.dto.request.CategoryRequest;
import com.eccomerce.nb_eccomerce.dto.response.CategoryResponse;

import java.util.List;

public interface CategoryService {
    CategoryResponse createCategory(CategoryRequest request);
    List<CategoryResponse> getAllCategories();
    void deleteCategory(Long categoryId);
}
