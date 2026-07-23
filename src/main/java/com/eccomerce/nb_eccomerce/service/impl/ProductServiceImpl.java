package com.eccomerce.nb_eccomerce.service.impl;


import com.eccomerce.nb_eccomerce.dto.request.ProductRequest;
import com.eccomerce.nb_eccomerce.dto.response.ProductResponse;
import com.eccomerce.nb_eccomerce.entity.Category;
import com.eccomerce.nb_eccomerce.entity.Product;
import com.eccomerce.nb_eccomerce.mapper.ProductMapper;
import com.eccomerce.nb_eccomerce.repository.ProductRepository;
import com.eccomerce.nb_eccomerce.service.ProductService;
import com.eccomerce.nb_eccomerce.service.helper.CategoryHelper;
import com.eccomerce.nb_eccomerce.service.helper.CommonHelper;
import com.eccomerce.nb_eccomerce.service.helper.ProductHelper;
import com.eccomerce.nb_eccomerce.util.PageableUtil;
import com.eccomerce.nb_eccomerce.util.PaginationUtil;
import com.eccomerce.nb_eccomerce.wrapper.PagedResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CommonHelper commonHelper;
    private final ProductHelper productHelper;
    private final CategoryHelper categoryHelper;
    private final ProductMapper productMapper;



    @Override
    public ProductResponse createProduct(ProductRequest request) {
        String normalizedName= commonHelper.normalize(request.getProductName());
        productHelper.validateDuplicateName(normalizedName,null);

        Product product=productMapper.toEntity(request);
        product.setProductName(normalizedName);

        Category category = categoryHelper.getCategoryOrThrow(request.getCategoryId());
        product.setCategory(category);

        Product savedProduct=productRepository.save(product);
        return productMapper.toResponse(savedProduct);
    }

    @Override
    public PagedResponse<ProductResponse> getAllProducts(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Pageable pageDetails= PageableUtil.create(pageNumber,pageSize,sortBy,sortDir);
        Page<Product> productPage=productRepository.findAll(pageDetails);
        List<ProductResponse>content= productPage.getContent()
                                      .stream()
                                      .map(productMapper::toResponse)
                                      .toList();

        return PaginationUtil.build(productPage,content);
    }
}
