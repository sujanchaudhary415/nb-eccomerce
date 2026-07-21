package com.eccomerce.nb_eccomerce.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryRequest {

    @NotBlank(message = "Category name must not be blank")
    @Size(min=4, max=50, message = "category name must be between 4-50 characters")
    @Pattern(
            regexp = "^[A-Za-z ]+$",
            message="Only letters and space allowed"
    )
    private String categoryName;


}
