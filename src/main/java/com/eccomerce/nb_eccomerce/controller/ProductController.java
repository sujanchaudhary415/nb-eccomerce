package com.eccomerce.nb_eccomerce.controller;

import com.eccomerce.nb_eccomerce.dto.request.ProductRequest;
import com.eccomerce.nb_eccomerce.dto.response.ProductResponse;
import com.eccomerce.nb_eccomerce.service.ProductService;
import com.eccomerce.nb_eccomerce.wrapper.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
}
