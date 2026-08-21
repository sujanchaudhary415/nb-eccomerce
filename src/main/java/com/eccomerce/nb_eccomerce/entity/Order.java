package com.eccomerce.nb_eccomerce.entity;

import com.eccomerce.nb_eccomerce.enums.OrderStatus;
import com.eccomerce.nb_eccomerce.enums.PaymentStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @Email
    @Column(nullable = false)
    private String customerEmail;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private OrderStatus orderStatus = OrderStatus.PENDING;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private PaymentStatus paymentStatus=PaymentStatus.PENDING;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal totalAmount;

    @Embedded
    private ShippingAddress shippingAddress;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name="user_id",nullable = false)
    private User user;

    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<OrderItem> orderItems=new ArrayList<>();

    @OneToOne(mappedBy = "order",cascade = CascadeType.ALL)
    private Payment payment;

}
