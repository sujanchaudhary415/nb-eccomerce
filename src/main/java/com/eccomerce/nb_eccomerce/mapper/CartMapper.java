package com.eccomerce.nb_eccomerce.mapper;

import com.eccomerce.nb_eccomerce.dto.response.CartResponse;
import com.eccomerce.nb_eccomerce.entity.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",uses = CartItemMapper.class)
public interface CartMapper {
    @Mapping(source = "cartItems", target = "items")
    CartResponse toResponse(Cart cart);
}
