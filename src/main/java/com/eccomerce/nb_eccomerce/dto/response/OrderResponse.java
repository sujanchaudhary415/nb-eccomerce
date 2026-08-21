package com.eccomerce.nb_eccomerce.dto.response;

import com.eccomerce.nb_eccomerce.enums.OrderStatus;
import com.eccomerce.nb_eccomerce.enums.PaymentStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponse {
    private Long orderId;
    private Long userId;
    private String email;
    private BigDecimal totalAmount;
    private OrderStatus orderStatus;
    private PaymentStatus paymentStatus;
    private ShippingAddressResponse shippingAddress;
    private List<OrderItemResponse> orderItems;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
