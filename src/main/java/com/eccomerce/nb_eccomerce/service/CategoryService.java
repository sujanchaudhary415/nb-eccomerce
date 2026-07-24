package com.eccomerce.nb_eccomerce.service;

import com.eccomerce.nb_eccomerce.dto.request.CategoryRequest;
import com.eccomerce.nb_eccomerce.dto.request.CategoryUpdateRequest;
import com.eccomerce.nb_eccomerce.dto.response.CategoryResponse;
import com.eccomerce.nb_eccomerce.wrapper.PagedResponse;


public interface CategoryService {
    CategoryResponse createCategory(CategoryRequest request);
    PagedResponse<CategoryResponse> getAllCategories(int pageNumber, int pageSize, String sortBy, String sortDir);
    void deleteCategoryById(Long categoryId);
    CategoryResponse getCategoryById(Long categoryId);
    CategoryResponse updateCategoryById(Long categoryId, CategoryUpdateRequest request);
}
