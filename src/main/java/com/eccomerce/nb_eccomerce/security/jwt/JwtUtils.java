package com.eccomerce.nb_eccomerce.security.jwt;

import com.eccomerce.nb_eccomerce.security.services.UserDetailsImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@Component
public class JwtUtils {

    private static final Logger logger= LoggerFactory.getLogger(JwtUtils.class);

    @Value("${spring.app.jwtSecret}")
    private String jwtSecret;

    @Value("${spring.app.jwtCookie}")
    private String jwtCookie;
    @Value("${spring.app.jwtExpirationMs}")
    private Long jwtExpirationMs;

    @Value("${spring.app.jwtRefreshCookie}")
    private String jwtRefreshCookie;
    @Value("${spring.app.jwtRefreshExpirationMs}")
    private Long jwtRefreshExpirationMs;

    private SecretKey key;

    @PostConstruct
    public void init(){
        validateSecret(jwtSecret);
        this.key= Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
        logger.info("JWT key initialized successfully");
    }


    private void validateSecret(String secret){
        if(secret==null || secret.isBlank()){
            throw new IllegalArgumentException("JWT secret cannot be empty");
        }
        if(secret.length()<44){
            throw new IllegalArgumentException("JWT secret must be at least 44 chars");
        }
    }

    public String generateAccessToken(UserDetailsImpl userDetails){
        List<String>roles=userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        return Jwts.builder()
                .subject(userDetails.getUsername())
                .claim("roles",roles)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public ResponseCookie generateAccessTokenCookie(UserDetailsImpl userPrincipal){
        String jwt=generateAccessToken(userPrincipal);
        return ResponseCookie.from(jwtCookie,jwt)
                .path("/api")
                .maxAge(jwtExpirationMs/1000)
                .httpOnly(true)
                .secure(false)
                .sameSite("Lax")
                .build();
    }

    public String getAccessTokenFromCookies(HttpServletRequest request){
        Cookie cookie= WebUtils.getCookie(request,jwtCookie);
        return cookie !=null ? cookie.getValue():null;
    }

    public ResponseCookie getCleanAccessTokenCookie(){
        return ResponseCookie.from(jwtCookie, "")
                .path("/api")
                .httpOnly(true)
                .secure(false)
                .sameSite("Lax")
                .maxAge(0)
                .build();
    }

    //Refresh Token

    public String generateRefreshToken(UserDetailsImpl userDetails){
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .claim("type","refresh")
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + jwtRefreshExpirationMs))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public ResponseCookie generateRefreshTokenCookie(String refreshToken){
        return ResponseCookie.from(jwtRefreshCookie,refreshToken)
                .path("/api/auth/refresh")
                .sameSite("Lax")
                .secure(false)
                .httpOnly(true)
                .maxAge(jwtRefreshExpirationMs/1000)
                .build();
    }

    public String getRefreshTokenFromCookie(HttpServletRequest request){
        Cookie cookie=WebUtils.getCookie(request,jwtRefreshCookie);
        return cookie!=null ? cookie.getValue() :null;
    }

    public ResponseCookie getCleanRefreshTokenCookie(){
        return ResponseCookie.from(jwtRefreshCookie,null)
                .path("/api/auth/refresh")
                .httpOnly(true)
                .secure(false)
                .sameSite("Lax")
                .maxAge(0)
                .build();
    }

    //helper
    public Jws<Claims> parseJwtClaims(String token){
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token);
    }


    public boolean validateJwtToken(String token){
        try{
             parseJwtClaims(token);
            return true;
        }
        catch (Exception e){
            logger.warn("JWT validation failed : {} ",e.getClass().getSimpleName());
            return false;
        }
    }

    public String extractUsername(String token){
        try{
            return parseJwtClaims(token).getPayload().getSubject();
        }
        catch (Exception e)
        {
            logger.warn("Failed to extract username: {}", e.getMessage());
            return null;
        }
    }

    public Instant getExpirationFromJwtToken(String token) {

        return  parseJwtClaims(token)
                .getPayload()
                .getExpiration()
                .toInstant();
    }



}
