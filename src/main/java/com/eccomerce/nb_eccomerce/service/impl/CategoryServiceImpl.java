package com.eccomerce.nb_eccomerce.service.impl;

import com.eccomerce.nb_eccomerce.dto.request.CategoryRequest;
import com.eccomerce.nb_eccomerce.dto.response.CategoryResponse;
import com.eccomerce.nb_eccomerce.entity.Category;
import com.eccomerce.nb_eccomerce.mapper.CategoryMapper;
import com.eccomerce.nb_eccomerce.repository.CategoryRepository;
import com.eccomerce.nb_eccomerce.service.CategoryService;
import com.eccomerce.nb_eccomerce.service.helper.CategoryHelper;
import com.eccomerce.nb_eccomerce.service.helper.CommonHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

   private final CategoryRepository categoryRepository;
   private final CommonHelper commonHelper;
   private final CategoryHelper categoryHelper;
   private final CategoryMapper categoryMapper;

   @Override
   public CategoryResponse createCategory(CategoryRequest request) {
        String normalizedName=commonHelper.normalize(request.getCategoryName());
        categoryHelper.validateDuplicateName(normalizedName,null);
        Category category=categoryMapper.toEntity(request);
        category.setCategoryName(normalizedName);

        Category savedCategory=categoryRepository.save(category);

        return categoryMapper.toResponse(savedCategory);

    }

    @Override
    public List<CategoryResponse> getAllCategories() {

        List<Category> categories = categoryRepository.findAll();

        return categories.stream()
                .map(categoryMapper::toResponse)
                .toList();
    }

    @Override
    public void deleteCategory(Long categoryId) {

    }
}
