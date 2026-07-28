package com.eccomerce.nb_eccomerce.security.services.mapper;

import com.eccomerce.nb_eccomerce.entity.Role;
import com.eccomerce.nb_eccomerce.entity.User;
import com.eccomerce.nb_eccomerce.security.response.AuthResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface AuthMapper {

    @Mapping(target = "roles", expression = "java(mapRoles(user.getRoles()))")
    AuthResponse toAuthResponse(User user);

    default List<String> mapRoles(Set<Role> roles){
        return roles.stream()
                .map(role->role.getRoleName().name())
                .toList();
    }
}
