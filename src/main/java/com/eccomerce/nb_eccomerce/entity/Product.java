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
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@SQLDelete(sql = """
    UPDATE products SET deleted = true,deleted_at = now() WHERE product_id = ?\s""")
@SQLRestriction("deleted=false")
public class Product extends BaseEntity {
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

    @Column(name = "deleted_at")
    private LocalDateTime deletedAT;


}
