package com.eccomerce.nb_eccomerce.mapper;


import com.eccomerce.nb_eccomerce.dto.response.PaymentResponse;
import com.eccomerce.nb_eccomerce.entity.Payment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    PaymentResponse toResponse(Payment payment);
}
