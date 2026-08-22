package com.eccomerce.nb_eccomerce.service;

import com.eccomerce.nb_eccomerce.dto.request.OrderRequest;
import com.eccomerce.nb_eccomerce.dto.response.OrderResponse;

import java.util.List;

public interface OrderService {
    OrderResponse createOrder(OrderRequest request);
    OrderResponse getOrderById(Long orderId);
    List<OrderResponse> getMyOrders();
}
