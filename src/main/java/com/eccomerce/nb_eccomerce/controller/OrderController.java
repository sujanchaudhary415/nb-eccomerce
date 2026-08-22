package com.eccomerce.nb_eccomerce.controller;

import com.eccomerce.nb_eccomerce.constant.AppConstant;
import com.eccomerce.nb_eccomerce.dto.request.OrderRequest;
import com.eccomerce.nb_eccomerce.dto.response.OrderResponse;
import com.eccomerce.nb_eccomerce.enums.OrderStatus;
import com.eccomerce.nb_eccomerce.service.OrderService;
import com.eccomerce.nb_eccomerce.wrapper.ApiResponse;
import com.eccomerce.nb_eccomerce.wrapper.PagedResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/orders")
    public ResponseEntity<ApiResponse<OrderResponse>> createOrder(@Valid @RequestBody OrderRequest request){
        OrderResponse response = orderService.createOrder(request);
        return ResponseEntity.ok().body(ApiResponse.success(response,"Order created successfully"));
    }

    @GetMapping("/orders/{orderId}")
    public ResponseEntity<ApiResponse<OrderResponse>> getOrderById(@PathVariable Long orderId){
        OrderResponse response=orderService.getOrderById(orderId);
        return ResponseEntity.ok().body(ApiResponse.success(response,"Order fetched successfully"));
    }

    @GetMapping("/orders")
    public ResponseEntity<ApiResponse<List<OrderResponse>>> getMyOrders(){
        List<OrderResponse> response=orderService.getMyOrders();
        return ResponseEntity.ok().body(ApiResponse.success(response,"All order fetched successfully"));
    }

    @PatchMapping("/orders/{orderId}/cancel")
    public ResponseEntity<ApiResponse<OrderResponse>> cancelOrder(@PathVariable Long orderId){
        OrderResponse response=orderService.cancelOrder(orderId);
        return ResponseEntity.ok().body(ApiResponse.success(response,"Order cancelled successfully"));
    }

    @PatchMapping("/admin/orders/{orderId}/status")
    public ResponseEntity<ApiResponse<OrderResponse>> updateOrderStatus(@PathVariable Long orderId,
            @RequestParam OrderStatus status
    ) {
        OrderResponse response = orderService.updateOrderStatus(orderId, status);

        return ResponseEntity.ok().body(ApiResponse.success(response,"Order status updated"));
    }

    @GetMapping("/admin/orders")
    public ResponseEntity<ApiResponse<PagedResponse<OrderResponse>>> getAllOrders(@RequestParam(name="pageNumber",defaultValue = AppConstant.PAGE_NUMBER)int pageNumber, @RequestParam(name="pageSize",defaultValue= AppConstant.PAGE_SIZE)int pageSize, @RequestParam(name="sortBy",defaultValue = AppConstant.SORT_BY_ORDER)String sortBy, @RequestParam(name = "sortDir",defaultValue = AppConstant.SORT_DIR) String sortDir){

      PagedResponse<OrderResponse>response=orderService.getAllOrders(pageNumber, pageSize, sortBy, sortDir);
      return ResponseEntity.ok().body(ApiResponse.success(response,"All orders fetched successfully"));
    }

}
