package com.eccomerce.nb_eccomerce.service;

import com.eccomerce.nb_eccomerce.entity.Order;
import com.eccomerce.nb_eccomerce.entity.Payment;
import com.eccomerce.nb_eccomerce.enums.PaymentMethod;

public interface PaymentService {
    Payment createPayment(Order order, PaymentMethod paymentmethod);
}
