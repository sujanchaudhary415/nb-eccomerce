package com.eccomerce.nb_eccomerce.service.impl;

import com.eccomerce.nb_eccomerce.dto.request.AddressRequest;
import com.eccomerce.nb_eccomerce.dto.request.AddressUpdateRequest;
import com.eccomerce.nb_eccomerce.dto.response.AddressResponse;
import com.eccomerce.nb_eccomerce.entity.Address;
import com.eccomerce.nb_eccomerce.entity.User;
import com.eccomerce.nb_eccomerce.exception.ResourceNotFoundException;
import com.eccomerce.nb_eccomerce.mapper.AddressMapper;
import com.eccomerce.nb_eccomerce.repository.AddressRepository;
import com.eccomerce.nb_eccomerce.repository.UserRepository;
import com.eccomerce.nb_eccomerce.service.AddressService;
import com.eccomerce.nb_eccomerce.service.helper.UserHelper;
import com.eccomerce.nb_eccomerce.util.PageableUtil;
import com.eccomerce.nb_eccomerce.util.PaginationUtil;
import com.eccomerce.nb_eccomerce.wrapper.PagedResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final UserHelper userHelper;
    private final UserRepository userRepository;
    private final AddressMapper addressMapper;
    private final AddressRepository addressRepository;

    @Override
    @Transactional
    public AddressResponse createAddress(AddressRequest request) {
        Address address=addressMapper.toEntity(request);
        Long userId= userHelper.getAuthenticatedUserId();
        User user= userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","userId",userId));

        address.setUser(user);
        Address savedAddress=addressRepository.save(address);
        return addressMapper.toResponse(savedAddress);
    }

    @Override
    @Transactional(readOnly = true)
    public PagedResponse<AddressResponse> gettingAddresses(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Pageable pageDetails= PageableUtil.create(pageNumber,pageSize,sortBy,sortDir);
        Page<Address> addressPage=addressRepository.findAll(pageDetails);
        List<AddressResponse>content=addressPage.getContent().stream()
                .map(addressMapper::toResponse)
                .toList();

        return PaginationUtil.build(addressPage,content);

    }

    @Override
    @Transactional(readOnly = true)
    public AddressResponse gettingSpecificAddress(Long addressId) {
        Long userId= userHelper.getAuthenticatedUserId();
        Address address=addressRepository.findByAddressIdAndUserUserId(addressId,userId)
                .orElseThrow(()->new ResourceNotFoundException("Address","addressId",addressId));

        return addressMapper.toResponse(address);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AddressResponse> gettingAddressByUser() {
        Long userId= userHelper.getAuthenticatedUserId();
        List<Address>address=addressRepository.findByUserUserId(userId);
        return address.stream()
                .map(addressMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public AddressResponse updateAddress(Long addressId, AddressUpdateRequest request) {
        Long userId= userHelper.getAuthenticatedUserId();
        Address address=addressRepository.findByAddressIdAndUserUserId(addressId,userId).orElseThrow(()->new ResourceNotFoundException("Address","addressId",addressId));
        addressMapper.updateEntity(request,address);
        Address updatedAddress=addressRepository.save(address);
        return addressMapper.toResponse(updatedAddress);
    }


}
