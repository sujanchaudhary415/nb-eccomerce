package com.eccomerce.nb_eccomerce.mapper;


import com.eccomerce.nb_eccomerce.entity.Address;
import com.eccomerce.nb_eccomerce.entity.ShippingAddress;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ShippingAddressMapper {

    ShippingAddress toEntity(Address address);
}
