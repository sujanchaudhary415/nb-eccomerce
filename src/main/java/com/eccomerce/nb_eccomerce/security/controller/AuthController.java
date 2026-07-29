package com.eccomerce.nb_eccomerce.security.controller;

import com.eccomerce.nb_eccomerce.security.jwt.JwtUtils;
import com.eccomerce.nb_eccomerce.security.model.LoginResult;
import com.eccomerce.nb_eccomerce.security.request.LoginRequest;
import com.eccomerce.nb_eccomerce.security.request.SignupRequest;
import com.eccomerce.nb_eccomerce.security.response.AuthResponse;
import com.eccomerce.nb_eccomerce.security.response.LogoutResponse;
import com.eccomerce.nb_eccomerce.security.services.AuthService;
import com.eccomerce.nb_eccomerce.wrapper.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtUtils jwtUtils;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<AuthResponse>> signup(
            @Valid @RequestBody SignupRequest request) {

        AuthResponse response = authService.signup(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(response, "User created Successfully"));
    }

    @PostMapping("/signin")
    public ResponseEntity<ApiResponse<AuthResponse>> signin(@Valid @RequestBody LoginRequest request) {
        LoginResult result = authService.signin(request);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE, result.getJwtCookie().toString());
        headers.add(HttpHeaders.SET_COOKIE, result.getJwtRefreshCookie().toString());

        return ResponseEntity.ok()
                .headers(headers)
                .body(ApiResponse.success(result.getAuthResponse(), "Login successful"));
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<String>> refreshAccessCookie(HttpServletRequest request) {
        String tokenFromCookie = jwtUtils.getRefreshTokenFromCookie(request);
        ResponseCookie freshAccessCookie = authService.refreshAccessToken(tokenFromCookie);

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, freshAccessCookie.toString())
                .body(ApiResponse.success("Access token refreshed successfully"));

    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<String>> logout(HttpServletRequest request) {

        String refreshToken = jwtUtils.getRefreshTokenFromCookie(request);

        LogoutResponse logoutResponse = authService.logout(refreshToken);

        return ResponseEntity.ok()
                .headers(headers -> {
                    headers.add(HttpHeaders.SET_COOKIE, logoutResponse.getAccessCookie().toString());
                    headers.add(HttpHeaders.SET_COOKIE, logoutResponse.getRefreshCookie().toString());
                })
                .body(ApiResponse.success("Logout successful"));
    }
}