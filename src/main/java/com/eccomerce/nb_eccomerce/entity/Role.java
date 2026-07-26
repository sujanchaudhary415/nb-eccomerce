package com.eccomerce.nb_eccomerce.entity;

import com.eccomerce.nb_eccomerce.enums.AppRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "Roles")
@NoArgsConstructor
@AllArgsConstructor
public class Role extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roleId;

    @Enumerated(EnumType.STRING)
    @Column(length = 20,name="role_name")
    private AppRole roleName;

    public Role( AppRole roleName) {
        this.roleName = roleName;
    }
}
