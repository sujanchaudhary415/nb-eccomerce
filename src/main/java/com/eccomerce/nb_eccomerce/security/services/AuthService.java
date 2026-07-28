package com.eccomerce.nb_eccomerce.security.services;

import com.eccomerce.nb_eccomerce.security.model.LoginResult;
import com.eccomerce.nb_eccomerce.security.request.LoginRequest;
import com.eccomerce.nb_eccomerce.security.request.SignupRequest;
import com.eccomerce.nb_eccomerce.security.response.AuthResponse;

public interface AuthService {
    AuthResponse signup(SignupRequest request);
    LoginResult signin(LoginRequest request);
}
