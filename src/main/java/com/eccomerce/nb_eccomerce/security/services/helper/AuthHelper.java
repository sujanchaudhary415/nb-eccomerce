package com.eccomerce.nb_eccomerce.security.services.helper;


import com.eccomerce.nb_eccomerce.entity.Role;
import com.eccomerce.nb_eccomerce.enums.AppRole;
import com.eccomerce.nb_eccomerce.exception.DuplicateResourceException;
import com.eccomerce.nb_eccomerce.exception.ResourceNotFoundException;
import com.eccomerce.nb_eccomerce.repository.RoleRepository;
import com.eccomerce.nb_eccomerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class AuthHelper {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final SecurityHelper commonHelper;

    public void validateDuplicateUser(String username,String email){

        if(userRepository.existsByUsername(username)){
            throw new DuplicateResourceException("Username already exists");
        }
        if(userRepository.existsByEmail(email)){
            throw new DuplicateResourceException("Email already exists");
        }
    }

    public Set<Role> mapRolesFromStrings(Set<String> inputRoles){
        Set<Role>roles=new HashSet<>();

        if(inputRoles==null || inputRoles.isEmpty()){
            roles.add(getOrThrow(AppRole.ROLE_USER));
            return roles;
        }

        for(String r:inputRoles)
        {
            String normalizedRole=commonHelper.normalize(r);
            switch (normalizedRole){
                case "admin"->roles.add(getOrThrow(AppRole.ROLE_ADMIN));
                case "seller"->roles.add(getOrThrow(AppRole.ROLE_SELLER));
                default->roles.add(getOrThrow(AppRole.ROLE_USER));
            }
        }
        return roles;
    }


    public Role getOrThrow(AppRole appRole){
         return roleRepository.findByRoleName(appRole).orElseThrow(()->new ResourceNotFoundException("Role","name",appRole.name()));
    }
}
