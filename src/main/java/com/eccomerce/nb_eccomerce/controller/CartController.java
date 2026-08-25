package com.eccomerce.nb_eccomerce.controller;

import com.eccomerce.nb_eccomerce.dto.request.AddToCartRequest;
import com.eccomerce.nb_eccomerce.dto.request.UpdateCartItemRequest;
import com.eccomerce.nb_eccomerce.dto.response.CartResponse;
import com.eccomerce.nb_eccomerce.service.CartService;
import com.eccomerce.nb_eccomerce.wrapper.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Carts", description = "Cart management APIs")
public class CartController {

    private final CartService cartService;

    @PostMapping("/carts/products")
    @Operation(summary = "Add a product to the cart")
    ResponseEntity<ApiResponse<CartResponse>>addToCart(@Valid @RequestBody AddToCartRequest request){
        CartResponse response=cartService.addToCart(request);
        return ResponseEntity.ok().body(ApiResponse.success(response,"Product added to cart"));
    }

    @GetMapping("/carts")
    @Operation(summary = "Get the current user's cart")
    ResponseEntity<ApiResponse<CartResponse>> getCart(){
        CartResponse response=cartService.getCart();
        return ResponseEntity.ok().body(ApiResponse.success(response,"Cart retrieved successfully"));
    }

    @PutMapping("/carts/items/{cartItemId}")
    @Operation(summary = "Update cart item quantity")
    ResponseEntity<ApiResponse<CartResponse>> updateCartItem(@PathVariable Long cartItemId, @Valid @RequestBody UpdateCartItemRequest request){
        CartResponse response=cartService.updateCartItem(cartItemId,request);
        return ResponseEntity.ok().body(ApiResponse.success(response,"Cart item updated successfully"));
    }

    @DeleteMapping("/carts/items/{cartItemId}")
    @Operation(summary = "Remove a cart item")
    ResponseEntity<ApiResponse<CartResponse>> removeCartItem(@PathVariable Long cartItemId){
        CartResponse response=cartService.removeCartItem(cartItemId);
        return ResponseEntity.ok().body(ApiResponse.success(response,"Cart item deleted successfully"));
    }

    @DeleteMapping("/carts")
    @Operation(summary = "Clear the cart")
    ResponseEntity<ApiResponse<CartResponse>> clearCart(){
        CartResponse response=cartService.clearCart();
        return ResponseEntity.ok().body(ApiResponse.success(response,"Cart deleted successfully"));
    }

    @GetMapping("/admin/carts/all")
    @Operation(summary = "Get all user carts")
    ResponseEntity<ApiResponse<List<CartResponse>>> getAllCarts(){
        List<CartResponse> response=cartService.getAllCarts();
        return ResponseEntity.ok().body(ApiResponse.success(response,"All carts retrieved successfully"));
    }


}
