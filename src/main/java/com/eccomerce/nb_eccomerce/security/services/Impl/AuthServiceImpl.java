package com.eccomerce.nb_eccomerce.security.services.Impl;

import com.eccomerce.nb_eccomerce.entity.Role;
import com.eccomerce.nb_eccomerce.entity.User;
import com.eccomerce.nb_eccomerce.repository.UserRepository;
import com.eccomerce.nb_eccomerce.security.jwt.JwtUtils;
import com.eccomerce.nb_eccomerce.security.model.LoginResult;
import com.eccomerce.nb_eccomerce.security.request.LoginRequest;
import com.eccomerce.nb_eccomerce.security.request.SignupRequest;
import com.eccomerce.nb_eccomerce.security.response.AuthResponse;
import com.eccomerce.nb_eccomerce.security.services.AuthService;
import com.eccomerce.nb_eccomerce.security.services.RefreshTokenService;
import com.eccomerce.nb_eccomerce.security.services.helper.AuthHelper;
import com.eccomerce.nb_eccomerce.security.services.helper.SecurityHelper;
import com.eccomerce.nb_eccomerce.security.services.mapper.AuthMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;


@Service
@Transactional
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthHelper authHelper;
    private final SecurityHelper commonHelper;
    private final UserRepository userRepository;
    private final AuthMapper authMapper;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final RefreshTokenService refreshTokenService;

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

    @Override
    public LoginResult signin(LoginRequest request) {
        Authentication authentication=authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails= (UserDetailsImpl) authentication.getPrincipal();

        List<String>roles=userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        AuthResponse authResponse=authMapper.toAuthResponse(user);


        ResponseCookie jwtCookie=jwtUtils.generateAccessCookie(userDetails);
        String refreshToken=jwtUtils.generateRefreshToken(userDetails);

        refreshTokenService.saveRefreshToken(user,refreshToken);

        ResponseCookie jwtRefreshCookie=jwtUtils.generateRefreshTokenCookie(refreshToken);

        return new LoginResult(authResponse,jwtCookie,jwtRefreshCookie);
    }
}
