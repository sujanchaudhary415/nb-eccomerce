package com.eccomerce.nb_eccomerce.mapper;

import com.eccomerce.nb_eccomerce.dto.request.CategoryRequest;
import com.eccomerce.nb_eccomerce.dto.response.CategoryResponse;
import com.eccomerce.nb_eccomerce.entity.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    Category toEntity(CategoryRequest request);
    CategoryResponse toResponse(Category category);
}
