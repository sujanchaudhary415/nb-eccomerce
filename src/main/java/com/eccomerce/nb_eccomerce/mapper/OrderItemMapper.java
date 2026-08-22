package com.eccomerce.nb_eccomerce.mapper;

import com.eccomerce.nb_eccomerce.dto.response.OrderItemResponse;
import com.eccomerce.nb_eccomerce.entity.CartItem;
import com.eccomerce.nb_eccomerce.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {

    @Mapping(target = "order", ignore = true)
    @Mapping(target = "product", source = "product")
    @Mapping(target = "productName", source = "product.productName")
    @Mapping(target = "unitPrice", source = "product.price")
    @Mapping(target = "subtotal", ignore = true)
    OrderItem toOrderItem(CartItem cartItem);

    @Mapping(target = "productId", source = "product.productId")
    @Mapping(target="price",source = "unitPrice")
    OrderItemResponse toOrderItemResponse(OrderItem orderItem);
}
