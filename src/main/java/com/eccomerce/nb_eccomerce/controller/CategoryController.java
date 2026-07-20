package com.eccomerce.nb_eccomerce.controller;

import com.eccomerce.nb_eccomerce.dto.request.CategoryRequest;
import com.eccomerce.nb_eccomerce.dto.response.CategoryResponse;
import com.eccomerce.nb_eccomerce.service.CategoryService;
import com.eccomerce.nb_eccomerce.wrapper.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/api/public/categories")
    public ResponseEntity<ApiResponse<CategoryResponse>> createCategory(@RequestBody @Valid CategoryRequest request)
    {
       CategoryResponse response= categoryService.createCategory(request);
       return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(response,"Category added successfully"));
    }

    @GetMapping("/api/public/categories")
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> getAllCategory()
    {
        List<CategoryResponse> response= categoryService.getAllCategories();
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(response,"Category fetched Successfully"));
    }
}
