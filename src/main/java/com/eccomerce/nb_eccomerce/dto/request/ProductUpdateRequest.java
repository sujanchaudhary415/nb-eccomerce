package com.eccomerce.nb_eccomerce.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class ProductUpdateRequest {

    @Size(min = 5, max = 100, message = "Product name must be between 5 and 100 characters")
    @Pattern(
            regexp = "^[A-Za-z ]+$",
            message = "Product name can contain only letters separated by single spaces"
    )
    private String productName;

    @DecimalMin(value = "0.01", message = "Price must be greater than 0")
    private BigDecimal price;

    @Min(value = 0, message = "Stock cannot be negative")
    private Integer stock;

    private Long categoryId;
}