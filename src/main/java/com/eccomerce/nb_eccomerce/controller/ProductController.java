package com.eccomerce.nb_eccomerce.controller;

import com.eccomerce.nb_eccomerce.constant.AppConstant;
import com.eccomerce.nb_eccomerce.dto.request.ProductRequest;
import com.eccomerce.nb_eccomerce.dto.response.ProductResponse;
import com.eccomerce.nb_eccomerce.service.ProductService;
import com.eccomerce.nb_eccomerce.wrapper.ApiResponse;
import com.eccomerce.nb_eccomerce.wrapper.PagedResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/api/public/products")
    public ResponseEntity<ApiResponse<ProductResponse>> createProduct( @RequestBody @Valid ProductRequest request){
      ProductResponse response= productService.createProduct(request);
      return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(response,"Product created"));
    }

    @GetMapping("/api/public/products")
    public ResponseEntity<ApiResponse<PagedResponse<ProductResponse>>> getAllProducts(@RequestParam(name = "pageNumber",defaultValue = AppConstant.PAGE_NUMBER)int pageNumber,
                                                                        @RequestParam(name="pageSize",defaultValue = AppConstant.PAGE_SIZE)int pageSize,
                                                                       @RequestParam(name = "sortBy",defaultValue = AppConstant.SORT_BY_PRODUCT)String sortBy,
                                                                       @RequestParam(name="sortDir",defaultValue = AppConstant.SORT_DIR)String sortDir)
    {
        PagedResponse<ProductResponse> response=productService.getAllProducts(pageNumber,pageSize,sortBy,sortDir);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(response,"All products fetched successfully"));
    }
}
