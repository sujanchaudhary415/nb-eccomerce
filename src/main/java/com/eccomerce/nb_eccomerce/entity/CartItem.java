package com.eccomerce.nb_eccomerce.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="cart_items")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartItemId;

    @ManyToOne(fetch=FetchType.LAZY,optional = false)
    @JoinColumn(name = "cart_id",
                nullable = false)
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "product_id",
                nullable = false)
    private Product product;

    @Column(nullable = false)
    private Integer quantity;

    @Column(
            nullable = false,
            precision = 19,
            scale = 2
    )
    private double productPrice;
}
