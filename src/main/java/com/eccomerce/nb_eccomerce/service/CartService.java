package com.eccomerce.nb_eccomerce.service;

import com.eccomerce.nb_eccomerce.dto.request.AddToCartRequest;
import com.eccomerce.nb_eccomerce.dto.response.CartResponse;

public interface CartService {
    CartResponse addToCart(AddToCartRequest request);
    CartResponse getCart();
}
