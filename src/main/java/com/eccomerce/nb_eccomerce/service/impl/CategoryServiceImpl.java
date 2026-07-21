package com.eccomerce.nb_eccomerce.service.impl;

import com.eccomerce.nb_eccomerce.dto.request.CategoryRequest;
import com.eccomerce.nb_eccomerce.dto.response.CategoryResponse;
import com.eccomerce.nb_eccomerce.entity.Category;
import com.eccomerce.nb_eccomerce.mapper.CategoryMapper;
import com.eccomerce.nb_eccomerce.repository.CategoryRepository;
import com.eccomerce.nb_eccomerce.service.CategoryService;
import com.eccomerce.nb_eccomerce.service.helper.CategoryHelper;
import com.eccomerce.nb_eccomerce.service.helper.CommonHelper;
import com.eccomerce.nb_eccomerce.util.PageableUtil;
import com.eccomerce.nb_eccomerce.util.PaginationUtil;
import com.eccomerce.nb_eccomerce.wrapper.PagedResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public PagedResponse<CategoryResponse> getAllCategories(int pageNumber, int pageSize, String sortBy, String sortDir) {

        Pageable pageDetails= PageableUtil.create(pageNumber,pageSize,sortBy,sortDir);
        Page<Category> categoryPage=categoryRepository.findAll(pageDetails);
        List<CategoryResponse> content = categoryPage.getContent()
                .stream()
                .map(categoryMapper::toResponse)
                .toList();
        return PaginationUtil.build(categoryPage,content);

    }

    @Override
    public void deleteCategoryById(Long categoryId) {
       categoryHelper.getCategoryOrThrow(categoryId);
       categoryRepository.deleteById(categoryId);
    }

    @Override
    public CategoryResponse getCategoryById(Long categoryId) {
        Category category=categoryHelper.getCategoryOrThrow(categoryId);
        return categoryMapper.toResponse(category);
    }
}
