package com.eccomerce.nb_eccomerce.service;

import com.eccomerce.nb_eccomerce.dto.request.AddToCartRequest;
import com.eccomerce.nb_eccomerce.dto.request.UpdateCartItemRequest;
import com.eccomerce.nb_eccomerce.dto.response.CartResponse;

import java.util.List;

public interface CartService {
    CartResponse addToCart(AddToCartRequest request);
    CartResponse getCart();
    CartResponse updateCartItem(Long cartItemId, UpdateCartItemRequest updateCartItemRequest);
    CartResponse removeCartItem(Long cartItemId);
    CartResponse clearCart();
    List<CartResponse> getAllCarts();
}
