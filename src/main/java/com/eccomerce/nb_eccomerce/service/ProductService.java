package com.eccomerce.nb_eccomerce.service;

import com.eccomerce.nb_eccomerce.dto.request.ProductRequest;
import com.eccomerce.nb_eccomerce.dto.response.ProductResponse;
import com.eccomerce.nb_eccomerce.wrapper.PagedResponse;

public interface ProductService {
    ProductResponse createProduct(ProductRequest request);
    PagedResponse<ProductResponse>getAllProducts(int pageNumber, int pageSize, String sortBy,String sortDir);
}
