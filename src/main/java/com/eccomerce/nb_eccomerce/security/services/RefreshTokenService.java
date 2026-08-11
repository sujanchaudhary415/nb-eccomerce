package com.eccomerce.nb_eccomerce.security.services;

import com.eccomerce.nb_eccomerce.entity.RefreshToken;
import com.eccomerce.nb_eccomerce.entity.User;

import java.util.Optional;


public interface RefreshTokenService {
    RefreshToken createRefreshToken(User user);
    Optional<RefreshToken>findByToken(String token);
    RefreshToken verifyExpiration(RefreshToken refreshToken);
    RefreshToken rotateRefreshToken(RefreshToken oldToken);
    void deleteByToken(String refreshToken);
    void deleteByUser(User user);
}
