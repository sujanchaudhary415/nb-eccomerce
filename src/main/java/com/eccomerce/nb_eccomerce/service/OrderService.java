package com.eccomerce.nb_eccomerce.service;

import com.eccomerce.nb_eccomerce.dto.request.OrderRequest;
import com.eccomerce.nb_eccomerce.dto.response.OrderResponse;
import com.eccomerce.nb_eccomerce.enums.OrderStatus;
import com.eccomerce.nb_eccomerce.wrapper.PagedResponse;

import java.util.List;

public interface OrderService {
    OrderResponse createOrder(OrderRequest request);
    OrderResponse getOrderById(Long orderId);
    List<OrderResponse> getMyOrders();
    OrderResponse cancelOrder(Long orderId);
    OrderResponse updateOrderStatus(Long orderId, OrderStatus status);
    PagedResponse<OrderResponse> getAllOrders(int pageNumber,int pageSize,String sortBy,String sortDir);
}
