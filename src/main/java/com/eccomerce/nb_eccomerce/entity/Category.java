package com.eccomerce.nb_eccomerce.entity;


import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "categories")
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class Category extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long categoryId;

    @Column(unique = true,nullable = false,length = 100)
    private String categoryName;

    @Column(nullable = false)
    private String description;

}
