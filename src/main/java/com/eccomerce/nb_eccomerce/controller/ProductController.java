package com.eccomerce.nb_eccomerce.controller;

import com.eccomerce.nb_eccomerce.constant.AppConstant;
import com.eccomerce.nb_eccomerce.dto.request.ProductRequest;
import com.eccomerce.nb_eccomerce.dto.request.ProductUpdateRequest;
import com.eccomerce.nb_eccomerce.dto.response.ProductResponse;
import com.eccomerce.nb_eccomerce.service.ProductService;
import com.eccomerce.nb_eccomerce.wrapper.ApiResponse;
import com.eccomerce.nb_eccomerce.wrapper.PagedResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
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


    @GetMapping("/api/public/products/{productId}")
    public ResponseEntity<ApiResponse<ProductResponse>> getProductById(@PathVariable @Positive(message = "Product ID must be greater than 0") Long productId){
        ProductResponse response=productService.getProductById(productId);
        return ResponseEntity.ok().body(ApiResponse.success(response,"Product fetched successfully"));
    }

    @GetMapping("/api/public/categories/{categoryId}/products")
    public ResponseEntity<ApiResponse<PagedResponse<ProductResponse>>> getProductByCategory(@PathVariable   @Positive(message = "Category ID must be greater than 0") Long categoryId,@RequestParam(name = "pageNumber",defaultValue = AppConstant.PAGE_NUMBER)int pageNumber,
                                                                                                                          @RequestParam(name="pageSize",defaultValue = AppConstant.PAGE_SIZE)int pageSize,
                                                                                                                          @RequestParam(name = "sortBy",defaultValue = AppConstant.SORT_BY_PRODUCT)String sortBy,
                                                                                                                          @RequestParam(name = "sortDir",defaultValue = AppConstant.SORT_DIR)String sortDir){
        PagedResponse<ProductResponse>response=productService.getProductByCategory(categoryId,pageNumber,pageSize,sortBy,sortDir);
        return ResponseEntity.ok().body(ApiResponse.success(response,"Product fetched successfully"));
    }

    @DeleteMapping("/api/admin/products/{productId}")
    public ResponseEntity<ApiResponse<Void>> deleteProductById(@PathVariable @Positive(message = "Product ID must be greater than 0") Long productId ){
        productService.deleteProduct(productId);
        return ResponseEntity.ok().body(ApiResponse.success("Product deleted Successfully"));
    }

    @PatchMapping("/api/admin/products/{productId}")
    public ResponseEntity<ApiResponse<ProductResponse>> updateProductById(@PathVariable @Positive(message = "Product Id must be greater than 0") Long productId,@Valid @RequestBody ProductUpdateRequest request){
        ProductResponse response=productService.updateProductById(productId,request);
        return ResponseEntity.ok().body(ApiResponse.success(response,"Product updated successfully"));
    }
}
