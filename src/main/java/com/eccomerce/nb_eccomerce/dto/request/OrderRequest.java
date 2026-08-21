package com.eccomerce.nb_eccomerce.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequest {
    @NotNull(message = "Shipping address ID is required")
    private Long shippingAddressId;
}
