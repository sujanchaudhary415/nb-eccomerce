package com.eccomerce.nb_eccomerce.entity;

import com.eccomerce.nb_eccomerce.enums.UserStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Builder
@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = """
    UPDATE users
    SET deleted = true,
        deleted_at = now()
    WHERE user_id = ?
""")
@SQLRestriction("deleted=false")
@ToString(exclude = {
        "roles",
        "addresses",
        "products"
})
@EqualsAndHashCode(
        onlyExplicitlyIncluded = true,
        callSuper = false)
public class User extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long userId;

    @Column(nullable = false,unique = true)
    private String userName;

    @Column(nullable = false,unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatus status = UserStatus.ACTIVE;

    @Builder.Default
    @Column(nullable = false)
    private boolean deleted = false;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Builder.Default
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",joinColumns = @JoinColumn(name = "user_id"),
                                   inverseJoinColumns =@JoinColumn(name = "role_id") )
    private Set<Role>roles=new HashSet<>();


    @Builder.Default
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Address>addresses=new HashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "seller",fetch = FetchType.LAZY)
    private Set<Product>products=new HashSet<>();
}
