package com.eccomerce.nb_eccomerce.dto.request;


import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public class ProductRequest {
    @NotBlank(message = "Product name is required")
    @Size(min=5,max = 100,message = "Product name must be between 5 and 100")
    @Pattern(
            regexp="^[A-Za-z ]+$",
            message = "Only letters and space allowed"
    )
    private String productName;

    @NotNull(message = "Price is required")
    @DecimalMin(value="0.01",message = "Price must be greater than 0")
    private BigDecimal price;

    @NotNull(message = "Stock is required")
    @Min(value = 0,message = "Stock must be greater than 0")
    private Integer Stock;

    @NotNull(message = "Category is required")
    private Long categoryId;
}
