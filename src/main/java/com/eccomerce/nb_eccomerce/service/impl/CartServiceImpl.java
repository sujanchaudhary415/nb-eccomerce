package com.eccomerce.nb_eccomerce.service.impl;

import com.eccomerce.nb_eccomerce.dto.request.AddToCartRequest;
import com.eccomerce.nb_eccomerce.dto.response.CartItemResponse;
import com.eccomerce.nb_eccomerce.dto.response.CartResponse;
import com.eccomerce.nb_eccomerce.entity.Cart;
import com.eccomerce.nb_eccomerce.entity.CartItem;
import com.eccomerce.nb_eccomerce.entity.Product;
import com.eccomerce.nb_eccomerce.entity.User;
import com.eccomerce.nb_eccomerce.enums.ProductStatus;
import com.eccomerce.nb_eccomerce.exception.BadRequestException;
import com.eccomerce.nb_eccomerce.exception.ResourceNotFoundException;
import com.eccomerce.nb_eccomerce.mapper.CartItemMapper;
import com.eccomerce.nb_eccomerce.mapper.CartMapper;
import com.eccomerce.nb_eccomerce.repository.CartItemRepository;
import com.eccomerce.nb_eccomerce.repository.CartRepository;
import com.eccomerce.nb_eccomerce.repository.ProductRepository;
import com.eccomerce.nb_eccomerce.repository.UserRepository;
import com.eccomerce.nb_eccomerce.security.services.Impl.UserDetailsImpl;
import com.eccomerce.nb_eccomerce.service.CartService;
import com.eccomerce.nb_eccomerce.service.helper.CartHelper;
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
    private final CartItemRepository cartItemRepository;
    private final CartMapper cartMapper;
    private final CartItemMapper cartItemMapper;
    private final CartHelper cartHelper;

    @Override
    @Transactional
    public CartResponse addToCart(AddToCartRequest request) {

        Long userId=getAuthenticatedUserId();
        Product product=productRepository.findById(request.getProductId()).orElseThrow(()->new ResourceNotFoundException("Product","productId:",request.getProductId()));
        validateProduct(product, request.getQuantity());
        Cart cart=cartHelper.getOrCreateCart(userId);

        Optional<CartItem>existingCartItem=cartItemRepository.findByCartCartIdAndProductProductId(cart.getCartId(), product.getProductId());

        if(existingCartItem.isPresent()){
          updateCartItem(existingCartItem.get(), request.getQuantity(), product);
        }
        else {
           newCartItem(cart,product, request.getQuantity());
        }
        return buildCartResponse(cart);
    }

    @Override
    @Transactional(readOnly = true)
    public CartResponse getCart() {
        Long userId=getAuthenticatedUserId();
        Cart cart=cartHelper.getCart(userId);
        return buildCartResponse(cart);
    }

    private Long getAuthenticatedUserId(){
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails= (UserDetailsImpl) authentication.getPrincipal();
        return userDetails.getId();
    }

    private void validateProduct(Product product, int quantity){
        if(product.getStatus()!=ProductStatus.ACTIVE){
            throw new BadRequestException("Product is not available");
        }

        if(quantity> product.getStock()){
            throw new BadRequestException("Product out of stock");
        }
    }

    private void updateCartItem(CartItem cartItem,int requestedQuantity,Product product){
        int newQuantity=cartItem.getQuantity()+requestedQuantity;
        if(newQuantity> product.getStock()){
            throw new BadRequestException("Total quantity exceeds available stock");
        }
        cartItem.setQuantity(newQuantity);
        cartItem.setProductPrice(product.getPrice());
        cartItemRepository.save(cartItem);
    }

    private void newCartItem(Cart cart,Product product,int quantity){
        CartItem cartItem=new CartItem();

        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cartItem.setProductPrice(product.getPrice());
        cartItem.setQuantity(quantity);

        cart.getCartItems().add(cartItem);

        cartItemRepository.save(cartItem);
    }

    public CartResponse buildCartResponse(Cart cart){
        CartResponse response=cartMapper.toResponse(cart);
        response.setTotalItems(cartHelper.calculateTotalItems(cart));
        response.setTotalPrice(cartHelper.calculateTotalPrice(cart));

        return response;
    }
}
