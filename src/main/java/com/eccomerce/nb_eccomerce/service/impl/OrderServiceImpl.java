package com.eccomerce.nb_eccomerce.service.impl;

import com.eccomerce.nb_eccomerce.dto.request.OrderRequest;
import com.eccomerce.nb_eccomerce.dto.response.OrderResponse;
import com.eccomerce.nb_eccomerce.entity.*;
import com.eccomerce.nb_eccomerce.enums.OrderStatus;
import com.eccomerce.nb_eccomerce.exception.ResourceNotFoundException;
import com.eccomerce.nb_eccomerce.mapper.OrderMapper;
import com.eccomerce.nb_eccomerce.repository.CartRepository;
import com.eccomerce.nb_eccomerce.repository.OrderRepository;
import com.eccomerce.nb_eccomerce.repository.UserRepository;
import com.eccomerce.nb_eccomerce.service.OrderCreationService;
import com.eccomerce.nb_eccomerce.service.OrderService;
import com.eccomerce.nb_eccomerce.service.PaymentService;
import com.eccomerce.nb_eccomerce.service.helper.UserHelper;
import com.eccomerce.nb_eccomerce.util.PageableUtil;
import com.eccomerce.nb_eccomerce.util.PaginationUtil;
import com.eccomerce.nb_eccomerce.wrapper.PagedResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

    private final UserHelper userHelper;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final OrderCreationService orderCreationService;
    private final PaymentService paymentService;

    @Override
    @Transactional
    public OrderResponse createOrder(OrderRequest request) {

        Long userId = userHelper.getAuthenticatedUserId();
        User user = getUser(userId);
        Cart cart = getCart(userId);

        Order order = orderCreationService.create(cart,user,request);
        Payment payment=paymentService.createPayment(order,request.getPaymentMethod());
        order.setPayment(payment);
        Order savedOrder = orderRepository.save(order);
        cart.getCartItems().clear();
        return orderMapper.toResponse(savedOrder);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponse getOrderById(Long orderId) {
        Long userId=userHelper.getAuthenticatedUserId();
        Order order=orderRepository.findByOrderIdAndUserUserId(orderId,userId).orElseThrow(()->new ResourceNotFoundException("Order","orderId:",orderId));
        return orderMapper.toResponse(order);
    }

    @Override
    public List<OrderResponse> getMyOrders() {
        Long userId=userHelper.getAuthenticatedUserId();
        return orderRepository.findByUserUserIdOrderByCreatedAtDesc(userId)
                .stream()
                .map(orderMapper::toResponse)
                .toList();
    }

    @Override
    public OrderResponse cancelOrder(Long orderId) {

        Long userId = userHelper.getAuthenticatedUserId();

        Order order = orderRepository
                .findByOrderIdAndUserUserId(orderId, userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Order",
                                "orderId",
                                orderId
                        )
                );

        if (order.getOrderStatus() != OrderStatus.PENDING) {
            throw new IllegalStateException(
                    "Order cannot be cancelled in status: "
                            + order.getOrderStatus()
            );
        }

        restoreStock(order);
        order.setOrderStatus(OrderStatus.CANCELLED);

        return orderMapper.toResponse(order);
    }

    @Override
    @Transactional
    public OrderResponse updateOrderStatus(Long orderId, OrderStatus status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order","orderId",orderId));

        order.setOrderStatus(status);
        return orderMapper.toResponse(order);
    }

    @Override
    public PagedResponse<OrderResponse> getAllOrders(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Pageable pageDetails= PageableUtil.create(pageNumber,pageSize,sortBy,sortDir);
        Page<Order>orderPage=orderRepository.findAll(pageDetails);
        List<OrderResponse>content=orderPage.getContent()
                .stream()
                .map(orderMapper::toResponse)
                .toList();
        return PaginationUtil.build(orderPage,content);
    }


    private User getUser(Long userId){
        User user=userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","userId",userId));
        return user;
    }

    private Cart getCart(Long userId){
        Cart cart=cartRepository.findByUserUserId(userId).orElseThrow(()->new ResourceNotFoundException("Cart","userId",userId));
        return cart;
    }

    private void restoreStock(Order order) {

        for (OrderItem orderItem : order.getOrderItems()) {

            Product product = orderItem.getProduct();

            if (product == null) {
                throw new IllegalStateException(
                        "Product not found for order item: "
                                + orderItem.getOrderItemId()
                );
            }

            product.setStock(
                    product.getStock()
                            + orderItem.getQuantity()
            );
        }
    }
}