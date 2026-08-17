package com.eccomerce.nb_eccomerce.controller;

import com.eccomerce.nb_eccomerce.dto.request.AddToCartRequest;
import com.eccomerce.nb_eccomerce.dto.request.UpdateCartItemRequest;
import com.eccomerce.nb_eccomerce.dto.response.CartResponse;
import com.eccomerce.nb_eccomerce.service.CartService;
import com.eccomerce.nb_eccomerce.wrapper.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/carts/products")
    ResponseEntity<ApiResponse<CartResponse>>addToCart(@Valid @RequestBody AddToCartRequest request){
        CartResponse response=cartService.addToCart(request);
        return ResponseEntity.ok().body(ApiResponse.success(response,"Product added to cart"));
    }

    @GetMapping("/carts")
    ResponseEntity<ApiResponse<CartResponse>> getCart(){
        CartResponse response=cartService.getCart();
        return ResponseEntity.ok().body(ApiResponse.success(response,"Cart retrieved successfully"));
    }

    @PutMapping("/carts/items/{cartItemId}")
    ResponseEntity<ApiResponse<CartResponse>> updateCartItem(@PathVariable Long cartItemId, @Valid @RequestBody UpdateCartItemRequest request){
        CartResponse response=cartService.updateCartItem(cartItemId,request);
        return ResponseEntity.ok().body(ApiResponse.success(response,"Cart item updated successfully"));
    }

    @DeleteMapping("/carts/items/{cartItemId}")
    ResponseEntity<ApiResponse<CartResponse>> removeCartItem(@PathVariable Long cartItemId){
        CartResponse response=cartService.removeCartItem(cartItemId);
        return ResponseEntity.ok().body(ApiResponse.success(response,"Cart item deleted successfully"));
    }

    @DeleteMapping("/carts")
    ResponseEntity<ApiResponse<CartResponse>> clearCart(){
        CartResponse response=cartService.clearCart();
        return ResponseEntity.ok().body(ApiResponse.success(response,"Cart deleted successfully"));
    }

    @GetMapping("/admin/carts/all")
    ResponseEntity<ApiResponse<List<CartResponse>>> getAllCarts(){
        List<CartResponse> response=cartService.getAllCarts();
        return ResponseEntity.ok().body(ApiResponse.success(response,"All carts retrieved successfully"));
    }


}
