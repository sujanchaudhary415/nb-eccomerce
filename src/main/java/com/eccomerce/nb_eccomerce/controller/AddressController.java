package com.eccomerce.nb_eccomerce.controller;

import com.eccomerce.nb_eccomerce.constant.AppConstant;
import com.eccomerce.nb_eccomerce.dto.request.AddressRequest;
import com.eccomerce.nb_eccomerce.dto.request.AddressUpdateRequest;
import com.eccomerce.nb_eccomerce.dto.response.AddressResponse;
import com.eccomerce.nb_eccomerce.service.AddressService;
import com.eccomerce.nb_eccomerce.wrapper.ApiResponse;
import com.eccomerce.nb_eccomerce.wrapper.PagedResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AddressController {

    private final AddressService addressService;

    @PostMapping("/addresses")
    ResponseEntity<ApiResponse<AddressResponse>> createAddress(@RequestBody AddressRequest request){
        AddressResponse response=addressService.createAddress(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(response,"Address created successfully"));
    }

    @GetMapping("/admin/addresses")
    ResponseEntity<ApiResponse<PagedResponse<AddressResponse>>> getAddresses(@RequestParam(name="pageNumber",defaultValue = AppConstant.PAGE_NUMBER)int pageNumber, @RequestParam(name="pageSize",defaultValue= AppConstant.PAGE_SIZE)int pageSize, @RequestParam(name="sortBy",defaultValue = AppConstant.SORT_BY_ADDRESS)String sortBy, @RequestParam(name = "sortDir",defaultValue = AppConstant.SORT_DIR) String sortDir){
        PagedResponse<AddressResponse>response=addressService.gettingAddresses(pageNumber,pageSize,sortBy,sortDir);
        return ResponseEntity.ok().body(ApiResponse.success(response,"Addresses fetched successfully"));
    }

    @GetMapping("/addresses/{addressId}")
    ResponseEntity<ApiResponse<AddressResponse>> getSpecificAddress(@PathVariable Long addressId){
        AddressResponse response=addressService.gettingSpecificAddress(addressId);
        return ResponseEntity.ok().body(ApiResponse.success(response,"Address fetched successfully"));
    }

    @GetMapping("/addresses")
    ResponseEntity<ApiResponse<List<AddressResponse>>> gettingAddressByUser(){
        List<AddressResponse> response=addressService.gettingAddressByUser();
        return ResponseEntity.ok().body(ApiResponse.success(response,"Addresses fetched successfully"));
    }

    @PatchMapping("/addresses/{addressId}")
    ResponseEntity<ApiResponse<AddressResponse>>updateAddress(@PathVariable Long addressId, @Valid @RequestBody AddressUpdateRequest request){
        AddressResponse response=addressService.updateAddress(addressId,request);
        return ResponseEntity.ok().body(ApiResponse.success(response,"Address updated successfully"));
    }

}
