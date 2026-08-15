package com.eccomerce.nb_eccomerce.service.impl;

import com.eccomerce.nb_eccomerce.dto.request.AddToCartRequest;
import com.eccomerce.nb_eccomerce.dto.response.CartResponse;
import com.eccomerce.nb_eccomerce.entity.Cart;
import com.eccomerce.nb_eccomerce.entity.CartItem;
import com.eccomerce.nb_eccomerce.entity.Product;
import com.eccomerce.nb_eccomerce.entity.User;
import com.eccomerce.nb_eccomerce.enums.ProductStatus;
import com.eccomerce.nb_eccomerce.exception.BadRequestException;
import com.eccomerce.nb_eccomerce.exception.ResourceNotFoundException;
import com.eccomerce.nb_eccomerce.mapper.CartMapper;
import com.eccomerce.nb_eccomerce.repository.CartItemRepository;
import com.eccomerce.nb_eccomerce.repository.CartRepository;
import com.eccomerce.nb_eccomerce.repository.ProductRepository;
import com.eccomerce.nb_eccomerce.repository.UserRepository;
import com.eccomerce.nb_eccomerce.security.services.Impl.UserDetailsImpl;
import com.eccomerce.nb_eccomerce.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final CartItemRepository cartItemRepository;
    private final CartMapper cartMapper;

    @Override
    @Transactional
    public CartResponse addToCart(AddToCartRequest request) {

        //Get Authenticated User
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails= (UserDetailsImpl) authentication.getPrincipal();
        Long userId=userDetails.getId();

        //Find the product
        Product product=productRepository.findById(request.getProductId()).orElseThrow(()->new ResourceNotFoundException("Product","productId:",request.getProductId()));

        //Check product Status
        if(product.getStatus()!= ProductStatus.ACTIVE){
            throw new BadRequestException("Product is not available");
        }

        //check stock
        if(request.getQuantity()>product.getStock()){
            throw new BadRequestException("Product is not available");
        }

        //find users cart
        Cart cart=cartRepository.findByUserUserId(userId).orElseGet(()->{User user=userRepository.findById(userId)
                    .orElseThrow(()->new ResourceNotFoundException("User","userId",userId));
            Cart newCart=Cart.builder()
                    .user(user)
                    .build();
            return cartRepository.save(newCart);
        });

        //check whether product already exist in cart it returns CartItem
        Optional<CartItem>existingCartItem=cartItemRepository.findByCartCartIdAndProductProductId(cart.getCartId(), product.getProductId());

        if(existingCartItem.isPresent()){
            CartItem cartItem=existingCartItem.get();

            int newQuantity=cartItem.getQuantity()+ request.getQuantity();

            if(newQuantity> product.getStock()){
                throw new BadRequestException("Total quantity exceeds available stock");
            }

            cartItem.setQuantity(newQuantity);
            cartItem.setProductPrice(product.getPrice());

            cartItemRepository.save(cartItem);
        }
        else {
            CartItem cartItem=new CartItem();

            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(request.getQuantity());
            cartItem.setProductPrice(product.getPrice());

            cart.getCartItems().add(cartItem);
            cartRepository.save(cart);

        }
        return cartMapper.toCartResponse(cart);
    }
}
