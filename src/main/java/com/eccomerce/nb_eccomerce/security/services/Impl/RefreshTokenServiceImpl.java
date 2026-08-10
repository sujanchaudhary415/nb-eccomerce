package com.eccomerce.nb_eccomerce.security.services.Impl;

import com.eccomerce.nb_eccomerce.entity.RefreshToken;
import com.eccomerce.nb_eccomerce.entity.User;
import com.eccomerce.nb_eccomerce.repository.RefreshTokenRepository;
import com.eccomerce.nb_eccomerce.security.jwt.JwtUtils;
import com.eccomerce.nb_eccomerce.security.services.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class RefreshTokenServiceImpl implements RefreshTokenService {

    @Value("${spring.app.jwtRefreshExpirationMs}")
    private Long jwtRefreshExpirationMs;

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtUtils jwtUtils;


    @Override
    public RefreshToken createRefreshToken(User user) {
        // Reuse entity if exists (single active session), or instantiate new
        RefreshToken refreshToken = refreshTokenRepository.findByUser(user)
                .orElseGet(() -> {
                    RefreshToken token = new RefreshToken();
                    token.setUser(user);
                    return token;
                });

        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiryDate(Instant.now().plusMillis(jwtRefreshExpirationMs));

        return refreshTokenRepository.save(refreshToken);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<RefreshToken> findByToken(String token) {
       return refreshTokenRepository.findByToken(token);

    }

    @Override
    @Transactional(readOnly = true)
    public RefreshToken verifyExpiration(RefreshToken refreshToken) {
        if(refreshToken.getExpiryDate().isBefore(Instant.now())){
            refreshTokenRepository.delete(refreshToken);
            throw new RuntimeException("Refresh token has Expired. Please login again");
        }
        return refreshToken;
    }

    @Override
    @Transactional
    public RefreshToken rotateRefreshToken(RefreshToken oldToken) {
        // Update the existing entity with a new UUID and fresh expiry date
        oldToken.setToken(UUID.randomUUID().toString());
        //oldToken.setExpiryDate(Instant.now().plusMillis(jwtRefreshExpirationMs));

        return refreshTokenRepository.save(oldToken);
    }

    @Override
    @Transactional
    public void deleteByToken(String refreshToken) {
        refreshTokenRepository.deleteByToken(refreshToken);
    }

    @Override
    public void deleteByUser(User user) {
        refreshTokenRepository.deleteByUser(user);
    }


}
