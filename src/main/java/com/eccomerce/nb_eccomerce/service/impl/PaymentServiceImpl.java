package com.eccomerce.nb_eccomerce.service.impl;

import com.eccomerce.nb_eccomerce.entity.Order;
import com.eccomerce.nb_eccomerce.entity.Payment;
import com.eccomerce.nb_eccomerce.enums.PaymentGateway;
import com.eccomerce.nb_eccomerce.enums.PaymentGatewayStatus;
import com.eccomerce.nb_eccomerce.enums.PaymentMethod;
import com.eccomerce.nb_eccomerce.service.PaymentService;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Override
    public Payment createPayment(Order order, PaymentMethod paymentmethod) {

        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setAmount(order.getTotalAmount());
        payment.setPaymentMethod(paymentmethod);

        if (paymentmethod == PaymentMethod.COD) {

            payment.setPgName(null);
            payment.setPgPaymentId(null);
            payment.setPgStatus(null);

        } else {
            payment.setPgName(PaymentGateway.RAZORPAY);
            payment.setPgPaymentId(null);
            payment.setPgStatus(PaymentGatewayStatus.PENDING);
        }

        return payment;
    }
}
