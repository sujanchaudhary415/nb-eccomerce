package com.eccomerce.nb_eccomerce.service.helper;

import com.eccomerce.nb_eccomerce.dto.response.CartResponse;
import com.eccomerce.nb_eccomerce.entity.Cart;
import com.eccomerce.nb_eccomerce.entity.CartItem;
import com.eccomerce.nb_eccomerce.entity.User;
import com.eccomerce.nb_eccomerce.exception.ResourceNotFoundException;
import com.eccomerce.nb_eccomerce.repository.CartRepository;
import com.eccomerce.nb_eccomerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class CartHelper {
    private final CartRepository cartRepository;
    private final UserRepository userRepository;

    public int calculateTotalItems(Cart cart){
        return cart.getCartItems()
                .stream()
                .mapToInt(CartItem::getQuantity)
                .sum();
    }

    public BigDecimal calculateTotalPrice(Cart cart){
        return cart.getCartItems()
                .stream()
                .map(cartItem -> cartItem.getProductPrice()
                        .multiply(BigDecimal.valueOf(cartItem.getQuantity())))
                .reduce(BigDecimal.ZERO,BigDecimal::add);
    }

    public Cart getOrCreateCart(Long userId){
        return cartRepository.findByUserUserId(userId).orElseGet(()->{
            User user=userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","userId",userId));

            Cart newCart=Cart.builder()
                    .user(user)
                    .build();
            return cartRepository.save(newCart);
        });
    }

    public Cart getCart(Long userId){
        Cart cart=cartRepository.findByUserUserId(userId).orElseThrow(()->new ResourceNotFoundException("Cart","userId",userId));
        return cart;
    }

}
