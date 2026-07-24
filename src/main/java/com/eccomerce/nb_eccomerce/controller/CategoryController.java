package com.eccomerce.nb_eccomerce.controller;

import com.eccomerce.nb_eccomerce.constant.AppConstant;
import com.eccomerce.nb_eccomerce.dto.request.CategoryRequest;
import com.eccomerce.nb_eccomerce.dto.request.CategoryUpdateRequest;
import com.eccomerce.nb_eccomerce.dto.response.CategoryResponse;
import com.eccomerce.nb_eccomerce.service.CategoryService;
import com.eccomerce.nb_eccomerce.wrapper.ApiResponse;
import com.eccomerce.nb_eccomerce.wrapper.PagedResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


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
    public ResponseEntity<ApiResponse<PagedResponse<CategoryResponse>>> getAllCategory(@RequestParam(name="pageNumber",defaultValue = AppConstant.PAGE_NUMBER)int pageNumber,
                                                                              @RequestParam(name="pageSize",defaultValue= AppConstant.PAGE_SIZE)int pageSize,
                                                                              @RequestParam(name="sortBy",defaultValue = AppConstant.SORT_BY_CATEGORY)String sortBy,
                                                                              @RequestParam(name = "sortDir",defaultValue = AppConstant.SORT_DIR) String sortDir)
    {
        PagedResponse<CategoryResponse> response= categoryService.getAllCategories(pageNumber,pageSize,sortBy,sortDir);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(response,"Category fetched Successfully"));
    }

    @DeleteMapping("/api/public/categories/{categoryId}")
    public ResponseEntity<ApiResponse<Void>> deleteCategory( @PathVariable  @Positive(message = "Category ID must be greater than 0") Long categoryId)
    {
        categoryService.deleteCategoryById(categoryId);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("Category Deleted Successfully"));
    }

    @GetMapping("/api/public/categories/{categoryId}")
    public ResponseEntity<ApiResponse<CategoryResponse>> getCategoryById(@PathVariable  @Positive(message = "Category ID must be greater than 0") Long categoryId){
        CategoryResponse category= categoryService.getCategoryById(categoryId);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(category,"fetched successfully"));
    }

    @PatchMapping("/api/admin/categories/{categoryId}")
    public ResponseEntity<ApiResponse<CategoryResponse>> updateCategoryById(@PathVariable @Positive(message = "Category Id must be greater than 0") Long categoryId, @Valid @RequestBody CategoryUpdateRequest request){
        CategoryResponse response=categoryService.updateCategoryById(categoryId,request);
        return ResponseEntity.ok().body(ApiResponse.success(response,"Category updated Successfully"));
    }

}
