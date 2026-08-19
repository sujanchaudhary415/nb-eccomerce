package com.eccomerce.nb_eccomerce.mapper;

import com.eccomerce.nb_eccomerce.dto.request.AddressRequest;
import com.eccomerce.nb_eccomerce.dto.request.AddressUpdateRequest;
import com.eccomerce.nb_eccomerce.dto.response.AddressResponse;
import com.eccomerce.nb_eccomerce.entity.Address;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    Address toEntity(AddressRequest request);
    AddressResponse toResponse(Address address);

    @BeanMapping(
            nullValuePropertyMappingStrategy =
                    NullValuePropertyMappingStrategy.IGNORE
    )
    void updateEntity(AddressUpdateRequest request, @MappingTarget  Address address);
}
