package com.eccomerce.nb_eccomerce.service.impl;

import com.eccomerce.nb_eccomerce.dto.request.OrderRequest;
import com.eccomerce.nb_eccomerce.entity.*;
import com.eccomerce.nb_eccomerce.enums.OrderStatus;
import com.eccomerce.nb_eccomerce.enums.PaymentStatus;
import com.eccomerce.nb_eccomerce.enums.ProductStatus;
import com.eccomerce.nb_eccomerce.exception.ResourceNotFoundException;
import com.eccomerce.nb_eccomerce.mapper.OrderItemMapper;
import com.eccomerce.nb_eccomerce.mapper.ShippingAddressMapper;
import com.eccomerce.nb_eccomerce.repository.AddressRepository;
import com.eccomerce.nb_eccomerce.repository.ProductRepository;
import com.eccomerce.nb_eccomerce.service.OrderCreationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class OrderCreationServiceImpl implements OrderCreationService {

    private final AddressRepository addressRepository;
    private final ShippingAddressMapper shippingAddressMapper;
    private final OrderItemMapper orderItemMapper;
    private final ProductRepository productRepository;

    @Override
    public Order create(Cart cart, User user, OrderRequest request) {

        validateCart(cart);
        Address address = getAddress(
                request.getShippingAddressId(),
                user.getUserId()
        );
        Order order = createOrderEntity(user, address);
        addOrderItems(order, cart);
        order.setTotalAmount(calculateTotal(order));
        return order;
    }

    private void validateCart(Cart cart) {

        if (cart.getCartItems() == null ||
                cart.getCartItems().isEmpty()) {

            throw new IllegalStateException(
                    "Cannot create order with an empty cart"
            );
        }
    }

    private Address getAddress(Long addressId, Long userId) {

        return addressRepository
                .findByAddressIdAndUserUserId(
                        addressId,
                        userId
                )
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Address",
                                "addressId",
                                addressId
                        )
                );
    }

    private Order createOrderEntity(
            User user,
            Address address
    ) {

        Order order = new Order();

        order.setUser(user);
        order.setCustomerEmail(user.getEmail());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setPaymentStatus(PaymentStatus.PENDING);

        ShippingAddress shippingAddress =
                shippingAddressMapper.toEntity(address);

        order.setShippingAddress(shippingAddress);

        return order;
    }

    private void addOrderItems(
            Order order,
            Cart cart
    ) {

        for (CartItem cartItem : cart.getCartItems()) {

            Product product = productRepository.findByIdForUpdate(cartItem.getProduct().getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product","productId",cartItem.getProduct().getProductId()));
            validateProduct(product, cartItem);
            decreaseStock(product, cartItem.getQuantity());
            OrderItem orderItem = orderItemMapper.toOrderItem(cartItem);

            BigDecimal subtotal = product.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()));

            orderItem.setSubtotal(subtotal);
            orderItem.setOrder(order);

            order.getOrderItems().add(orderItem);
        }
    }

    private void validateProduct(
            Product product,
            CartItem cartItem
    ) {

        if (product.getStatus() != ProductStatus.ACTIVE) {

            throw new IllegalStateException(
                    "Product is no longer available: "
                            + product.getProductName()
            );
        }

        if (product.getStock() < cartItem.getQuantity()) {

            throw new IllegalStateException(
                    "Insufficient stock for product: "
                            + product.getProductName()
            );
        }
    }

    private void decreaseStock(
            Product product,
            int quantity
    ) {

        product.setStock(
                product.getStock() - quantity
        );
    }

    private BigDecimal calculateTotal(Order order) {

        return order.getOrderItems()
                .stream()
                .map(OrderItem::getSubtotal)
                .reduce(
                        BigDecimal.ZERO,
                        BigDecimal::add
                );
    }
}