package com.eccomerce.nb_eccomerce.security.services;

import com.eccomerce.nb_eccomerce.entity.RefreshToken;
import com.eccomerce.nb_eccomerce.entity.User;

import java.util.Optional;


public interface RefreshTokenService {
    RefreshToken saveRefreshToken(User user,String refreshToken);
    Optional<RefreshToken>findByToken(String token);
    RefreshToken verifyExpiration(RefreshToken refreshToken);
    RefreshToken rotateRefreshToken(User user,String newRefreshToken);
    void deleteByUser(User user);

    void deleteByToken(String refreshToken);
}
