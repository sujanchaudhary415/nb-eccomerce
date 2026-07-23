package com.eccomerce.nb_eccomerce.mapper;

import com.eccomerce.nb_eccomerce.dto.request.ProductRequest;
import com.eccomerce.nb_eccomerce.dto.response.ProductResponse;
import com.eccomerce.nb_eccomerce.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product  toEntity(ProductRequest request);

    @Mapping(target = "categoryId",source = "category.categoryId")
    @Mapping(target="categoryName",source="category.categoryName")
    ProductResponse toResponse(Product product);
}
