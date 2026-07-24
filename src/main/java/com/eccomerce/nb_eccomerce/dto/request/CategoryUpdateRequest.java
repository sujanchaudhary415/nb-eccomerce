package com.eccomerce.nb_eccomerce.dto.request;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryUpdateRequest {
    @Size(min = 4, max = 50,
            message = "Category name must be between 4 and 50 characters")
    @Pattern(
            regexp = "^[A-Za-z ]+$",
            message = "Category name can contain only letters separated by single spaces"
    )
    private String categoryName;

    @Size(min = 10, max = 100,
            message = "Description must be between 10 and 100 characters")
    private String description;
}
