package com.eccomerce.nb_eccomerce.security.model;

import com.eccomerce.nb_eccomerce.security.response.AuthResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.ResponseCookie;


@Getter
@AllArgsConstructor
public class LoginResult {
    private final AuthResponse authResponse;
    private final ResponseCookie accessCookie;
    private final ResponseCookie refreshCookie;
}
