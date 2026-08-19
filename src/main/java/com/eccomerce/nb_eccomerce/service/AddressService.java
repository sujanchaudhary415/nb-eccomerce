package com.eccomerce.nb_eccomerce.service;

import com.eccomerce.nb_eccomerce.dto.request.AddressRequest;
import com.eccomerce.nb_eccomerce.dto.request.AddressUpdateRequest;
import com.eccomerce.nb_eccomerce.dto.response.AddressResponse;
import com.eccomerce.nb_eccomerce.wrapper.PagedResponse;

import java.util.List;


public interface AddressService {
    AddressResponse createAddress(AddressRequest request);
    PagedResponse<AddressResponse> gettingAddresses(int pageNumber,int pageSize,String sortBy,String sortDir);
    AddressResponse gettingSpecificAddress(Long addressId);
    List<AddressResponse> gettingAddressByUser();
    AddressResponse updateAddress(Long addressId, AddressUpdateRequest request);
}
