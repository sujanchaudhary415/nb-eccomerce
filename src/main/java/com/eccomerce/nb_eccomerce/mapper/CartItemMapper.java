package com.eccomerce.nb_eccomerce.mapper;

import com.eccomerce.nb_eccomerce.dto.response.CartItemResponse;
import com.eccomerce.nb_eccomerce.entity.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;

@Mapper(componentModel = "spring")
public interface CartItemMapper {
    @Mapping(source = "product.productId",target="productId")
    @Mapping(source = "product.productName",target="productName")
    @Mapping(source = "productPrice",target="productPrice")
    @Mapping(source = "quantity",target="quantity")
    @Mapping(
            target = "subtotal",
            expression = "java(calculateSubtotal(cartItem))"
    )
    CartItemResponse toResponse(CartItem cartItem);

    default BigDecimal calculateSubtotal(CartItem cartItem) {
        return cartItem.getProductPrice()
                .multiply(BigDecimal.valueOf(cartItem.getQuantity()));
    }
}
