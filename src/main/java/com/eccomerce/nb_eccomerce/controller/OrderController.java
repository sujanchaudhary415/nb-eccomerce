package com.eccomerce.nb_eccomerce.controller;

import com.eccomerce.nb_eccomerce.dto.request.OrderRequest;
import com.eccomerce.nb_eccomerce.dto.response.OrderResponse;
import com.eccomerce.nb_eccomerce.service.OrderService;
import com.eccomerce.nb_eccomerce.wrapper.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<ApiResponse<OrderResponse>> createOrder(@Valid @RequestBody OrderRequest request){
        OrderResponse response = orderService.createOrder(request);
        return ResponseEntity.ok().body(ApiResponse.success(response,"Order created successfully"));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<ApiResponse<OrderResponse>> getOrderById(@PathVariable Long orderId){
        OrderResponse response=orderService.getOrderById(orderId);
        return ResponseEntity.ok().body(ApiResponse.success(response,"Order fetched successfully"));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<OrderResponse>>> getMyOrders(){
        List<OrderResponse> response=orderService.getMyOrders();
        return ResponseEntity.ok().body(ApiResponse.success(response,"All order fetched successfully"));
    }

}
