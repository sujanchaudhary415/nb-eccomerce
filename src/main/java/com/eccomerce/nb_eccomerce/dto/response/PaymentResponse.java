package com.eccomerce.nb_eccomerce.dto.response;

import com.eccomerce.nb_eccomerce.enums.PaymentGateway;
import com.eccomerce.nb_eccomerce.enums.PaymentGatewayStatus;
import com.eccomerce.nb_eccomerce.enums.PaymentMethod;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentResponse {

    private Long paymentId;
    private BigDecimal amount;
    private PaymentMethod paymentMethod;
    private PaymentGateway pgName;
    private String pgPaymentId;
    private PaymentGatewayStatus pgStatus;
}