package com.eccomerce.nb_eccomerce.security.services.Impl;

import com.eccomerce.nb_eccomerce.entity.Role;
import com.eccomerce.nb_eccomerce.entity.User;
import com.eccomerce.nb_eccomerce.repository.UserRepository;
import com.eccomerce.nb_eccomerce.security.request.SignupRequest;
import com.eccomerce.nb_eccomerce.security.response.AuthResponse;
import com.eccomerce.nb_eccomerce.security.services.AuthService;
import com.eccomerce.nb_eccomerce.security.services.helper.AuthHelper;
import com.eccomerce.nb_eccomerce.security.services.helper.SecurityHelper;
import com.eccomerce.nb_eccomerce.security.services.mapper.AuthMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;


@Service
@Transactional
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthHelper authHelper;
    private final SecurityHelper commonHelper;
    private final UserRepository userRepository;
    private final AuthMapper authMapper;

    @Override
    @Transactional
    public AuthResponse signup(SignupRequest request) {
        String username= commonHelper.normalize(request.getUsername());
        String email=commonHelper.normalizeEmail(request.getEmail());

        authHelper.validateDuplicateUser(username,email);

        Set<Role>roles=authHelper.mapRolesFromStrings(request.getRoles());

        User user=new User();
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(request.getPassword());
        user.setRoles(roles);

        User savedUser=userRepository.save(user);
        return authMapper.toAuthResponse(savedUser) ;
    }
}
