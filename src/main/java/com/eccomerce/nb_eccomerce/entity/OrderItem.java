package com.eccomerce.nb_eccomerce.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name="order_id",nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name="product_id",nullable = false)
    private Product product;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private Integer quantity;

    @Column(
            nullable = false,
            precision = 19,
            scale = 2
    )
    private BigDecimal unitPrice;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal subtotal;

}
