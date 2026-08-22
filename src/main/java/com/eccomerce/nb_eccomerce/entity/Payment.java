package com.eccomerce.nb_eccomerce.entity;

import com.eccomerce.nb_eccomerce.enums.PaymentGateway;
import com.eccomerce.nb_eccomerce.enums.PaymentGatewayStatus;
import com.eccomerce.nb_eccomerce.enums.PaymentMethod;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "payments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    @OneToOne(fetch=FetchType.LAZY,optional = false)
    @JoinColumn(name = "order_id",nullable = false,unique = true)
    private Order order;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    private PaymentGateway pgName;

    @Column(unique = true)
    private String pgPaymentId;

    @Enumerated(EnumType.STRING)
    private PaymentGatewayStatus pgStatus;

    private String pgResponseMessage;

}
