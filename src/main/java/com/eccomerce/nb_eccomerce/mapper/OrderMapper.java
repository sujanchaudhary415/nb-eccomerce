package com.eccomerce.nb_eccomerce.mapper;

import com.eccomerce.nb_eccomerce.dto.response.OrderResponse;
import com.eccomerce.nb_eccomerce.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring",
        uses = {
                OrderItemMapper.class,
                PaymentMapper.class
        }
)
public interface OrderMapper {


    @Mapping(target="email",source="user.email")
    @Mapping(target="userId",source = "user.userId")
    OrderResponse toResponse(Order order);

}
