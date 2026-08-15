package com.eccomerce.nb_eccomerce.controller;

import com.eccomerce.nb_eccomerce.dto.request.AddToCartRequest;
import com.eccomerce.nb_eccomerce.dto.response.CartResponse;
import com.eccomerce.nb_eccomerce.service.CartService;
import com.eccomerce.nb_eccomerce.wrapper.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/products")
    ResponseEntity<ApiResponse<CartResponse>>addToCart(@Valid @RequestBody AddToCartRequest request){
        CartResponse response=cartService.addToCart(request);
        return ResponseEntity.ok().body(ApiResponse.success(response,"Product added to cart"));
    }
}
