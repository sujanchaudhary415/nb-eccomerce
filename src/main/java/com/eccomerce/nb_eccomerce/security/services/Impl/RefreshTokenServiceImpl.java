package com.eccomerce.nb_eccomerce.security.services.Impl;

import com.eccomerce.nb_eccomerce.entity.RefreshToken;
import com.eccomerce.nb_eccomerce.entity.User;
import com.eccomerce.nb_eccomerce.repository.RefreshTokenRepository;
import com.eccomerce.nb_eccomerce.security.jwt.JwtUtils;
import com.eccomerce.nb_eccomerce.security.services.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtUtils jwtUtils;

    @Override
    @Transactional
    public RefreshToken saveRefreshToken(User user, String refreshToken) {
     refreshTokenRepository.findByUser(user).ifPresent(refreshTokenRepository::delete);

     RefreshToken token=new RefreshToken();

     token.setUser(user);
     token.setToken(refreshToken);
     token.setExpiryDate(jwtUtils.getExpirationFromJwtToken(refreshToken));

     return refreshTokenRepository.save(token);
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
    public RefreshToken rotateRefreshToken(User user, String newRefreshToken) {
       RefreshToken refreshToken=refreshTokenRepository.findByUser(user).orElse(new RefreshToken());
       refreshToken.setUser(user);
       refreshToken.setToken(newRefreshToken);
       refreshToken.setExpiryDate(jwtUtils.getExpirationFromJwtToken(newRefreshToken));
       return refreshTokenRepository.save(refreshToken);
    }

    @Override
    @Transactional
    public void deleteByUser(User user) {
        refreshTokenRepository.deleteByUser(user);
    }

    @Override
    @Transactional
    public void delete(RefreshToken refreshToken) {
        refreshTokenRepository.delete(refreshToken);
    }


}
