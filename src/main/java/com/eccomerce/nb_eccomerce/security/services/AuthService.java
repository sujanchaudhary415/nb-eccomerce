package com.eccomerce.nb_eccomerce.security.services;

import com.eccomerce.nb_eccomerce.security.model.LoginResult;
import com.eccomerce.nb_eccomerce.security.request.LoginRequest;
import com.eccomerce.nb_eccomerce.security.request.SignupRequest;
import com.eccomerce.nb_eccomerce.security.response.AuthResponse;
import com.eccomerce.nb_eccomerce.security.response.LogoutResponse;
import org.springframework.http.ResponseCookie;

public interface AuthService {
    AuthResponse signup(SignupRequest request);
    LoginResult signin(LoginRequest request);
    ResponseCookie refreshAccessToken(String refreshToken);
    LogoutResponse logout(String refreshToken);
}
