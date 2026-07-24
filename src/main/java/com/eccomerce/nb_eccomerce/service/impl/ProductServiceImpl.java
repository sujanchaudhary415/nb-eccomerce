package com.eccomerce.nb_eccomerce.service.impl;

import com.eccomerce.nb_eccomerce.dto.request.ProductRequest;
import com.eccomerce.nb_eccomerce.dto.request.ProductUpdateRequest;
import com.eccomerce.nb_eccomerce.dto.response.ProductResponse;
import com.eccomerce.nb_eccomerce.entity.Category;
import com.eccomerce.nb_eccomerce.entity.Product;
import com.eccomerce.nb_eccomerce.exception.ResourceNotFoundException;
import com.eccomerce.nb_eccomerce.mapper.ProductMapper;
import com.eccomerce.nb_eccomerce.repository.CategoryRepository;
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
    private final CategoryRepository categoryRepository;



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

    @Override
    public ProductResponse getProductById(Long productId) {
       Product product= productHelper.getProductOrThrow(productId);
       return productMapper.toResponse(product);
    }

    @Override
    public PagedResponse<ProductResponse> getProductByCategory(Long categoryId, int pageNumber, int pageSize, String sortBy, String sortDir) {
        Pageable pageable=PageableUtil.create(pageNumber,pageSize,sortBy,sortDir);
        Category category=categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","Id",categoryId));
        Page<Product>productPage=productRepository.findByCategory_CategoryId(category.getCategoryId(),pageable);
        List<ProductResponse>content=productPage.getContent()
                .stream()
                .map(productMapper::toResponse)
                .toList();
        return PaginationUtil.build(productPage,content);
    }

    @Override
    public void deleteProduct(Long productId) {
       productHelper.getProductOrThrow(productId);
       productRepository.deleteById(productId);
    }

    @Override
    public ProductResponse updateProductById(Long productId, ProductUpdateRequest request) {
        Product product=productHelper.getProductOrThrow(productId);
        if(commonHelper.isNotBlank(request.getProductName())){
            String normalizedName= commonHelper.normalize(request.getProductName());
            productHelper.validateDuplicateName(normalizedName,productId);
            request.setProductName(normalizedName);
        }

        if (request.getCategoryId() != null) {
            Category category = categoryHelper.getCategoryOrThrow(request.getCategoryId());
            product.setCategory(category);
        }
        productMapper.updateProductFromRequest(request,product);
        productRepository.save(product);
        return productMapper.toResponse(product);
    }


}
