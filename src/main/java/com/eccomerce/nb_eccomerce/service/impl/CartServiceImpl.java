package com.eccomerce.nb_eccomerce.service.impl;

import com.eccomerce.nb_eccomerce.dto.request.AddToCartRequest;
import com.eccomerce.nb_eccomerce.dto.request.UpdateCartItemRequest;
import com.eccomerce.nb_eccomerce.dto.response.CartResponse;
import com.eccomerce.nb_eccomerce.entity.Cart;
import com.eccomerce.nb_eccomerce.entity.CartItem;
import com.eccomerce.nb_eccomerce.entity.Product;
import com.eccomerce.nb_eccomerce.enums.ProductStatus;
import com.eccomerce.nb_eccomerce.exception.BadRequestException;
import com.eccomerce.nb_eccomerce.exception.ResourceNotFoundException;
import com.eccomerce.nb_eccomerce.mapper.CartMapper;
import com.eccomerce.nb_eccomerce.repository.CartItemRepository;
import com.eccomerce.nb_eccomerce.repository.CartRepository;
import com.eccomerce.nb_eccomerce.repository.ProductRepository;
import com.eccomerce.nb_eccomerce.service.CartService;
import com.eccomerce.nb_eccomerce.service.helper.CartHelper;
import com.eccomerce.nb_eccomerce.service.helper.UserHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;
    private final CartMapper cartMapper;
    private final CartHelper cartHelper;
    private final CartRepository cartRepository;
    private final UserHelper userHelper;

    @Override
    @Transactional
    public CartResponse addToCart(AddToCartRequest request) {

        Long userId= userHelper.getAuthenticatedUserId();
        Product product=productRepository.findById(request.getProductId()).orElseThrow(()->new ResourceNotFoundException("Product","productId:",request.getProductId()));
        validateProduct(product, request.getQuantity());
        Cart cart=cartHelper.getOrCreateCart(userId);

        Optional<CartItem>existingCartItem=cartItemRepository.findByCartCartIdAndProductProductId(cart.getCartId(), product.getProductId());

        if(existingCartItem.isPresent()){
          increaseCartItemQuantity(existingCartItem.get(), request.getQuantity(), product);
        }
        else {
           newCartItem(cart,product, request.getQuantity());
        }
        return buildCartResponse(cart);
    }

    @Override
    @Transactional(readOnly = true)
    public CartResponse getCart() {
        Long userId=userHelper.getAuthenticatedUserId();
        Cart cart=cartHelper.getCart(userId);

        for (CartItem item : cart.getCartItems()) {

            BigDecimal currentPrice = item.getProduct().getPrice();

            if (item.getProductPrice().compareTo(currentPrice) != 0) {
                item.setProductPrice(currentPrice);
            }
        }
        return buildCartResponse(cart);
    }

    @Override
    @Transactional
    public CartResponse updateCartItem(Long cartItemId, UpdateCartItemRequest request) {
        Long userId=userHelper.getAuthenticatedUserId();
        Cart cart=cartHelper.getCart(userId);

        CartItem cartItem=cartItemRepository.findByCartCartIdAndCartItemId(cart.getCartId(),cartItemId).orElseThrow(()->new ResourceNotFoundException("CartItem","cartItemId",cartItemId));

        Product product=cartItem.getProduct();
        validateProduct(product,request.getQuantity());

        cartItem.setQuantity(request.getQuantity());
        cartItem.setProductPrice(product.getPrice());

        cartItemRepository.save(cartItem);
        return buildCartResponse(cart);
    }

    @Override
    @Transactional
    public CartResponse removeCartItem(Long cartItemId) {
        Long userId=userHelper.getAuthenticatedUserId();
        Cart cart=cartHelper.getCart(userId);
        CartItem cartItem=cartItemRepository.findByCartCartIdAndCartItemId(cart.getCartId(),cartItemId).orElseThrow(()->new ResourceNotFoundException("CartItem","CartItemId",cartItemId));

        cart.getCartItems().remove(cartItem);
        return buildCartResponse(cart);
    }

    @Override
    @Transactional
    public CartResponse clearCart() {
        Long userId=userHelper.getAuthenticatedUserId();
        Cart cart=cartHelper.getCart(userId);

        cart.getCartItems().clear();
        return buildCartResponse(cart);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CartResponse> getAllCarts() {
        List<Cart> carts=cartRepository.findAll();

        return carts.stream()
                .map(this::buildCartResponse)
                .toList();
    }

    private void validateProduct(Product product, int quantity){
        if(product.getStatus()!=ProductStatus.ACTIVE){
            throw new BadRequestException("Product is not available");
        }

        if(quantity> product.getStock()){
            throw new BadRequestException("Product out of stock");
        }
    }

    private void increaseCartItemQuantity(CartItem cartItem,int requestedQuantity,Product product){
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

    private CartResponse buildCartResponse(Cart cart){
        CartResponse response=cartMapper.toResponse(cart);
        response.setTotalItems(cartHelper.calculateTotalItems(cart));
        response.setTotalPrice(cartHelper.calculateTotalPrice(cart));

        return response;
    }
}
