package com.eccomerce.nb_eccomerce.dto.response;

import com.eccomerce.nb_eccomerce.enums.ProductStatus;
import lombok.*;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class ProductResponse {
    private Long productId;
    private String productName;
    private BigDecimal price;
    private Integer stock;
    private ProductStatus status;
    private Long categoryId;
    private String categoryName;
}
