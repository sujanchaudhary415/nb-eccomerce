package com.eccomerce.nb_eccomerce.entity;

import com.eccomerce.nb_eccomerce.enums.ProductStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@SQLDelete(sql = "UPDATE product SET deleted=true, deleted_at=now() Where id=? ")
@SQLRestriction("deleted=false")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long productId;

    @Column(nullable=false,unique = true,length = 100)
    private String productName;

    @Column(nullable = false)
    private BigDecimal price;

    private Integer stock;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "category_Id",nullable = false)
    private Category category;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductStatus status=ProductStatus.ACTIVE;


    @Builder.Default
    @Column(nullable = false)
    private boolean deleted=false;

    private LocalDateTime deletedAT;


}
