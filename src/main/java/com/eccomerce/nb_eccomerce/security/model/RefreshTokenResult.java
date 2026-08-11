package com.eccomerce.nb_eccomerce.security.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.ResponseCookie;

@Getter
@AllArgsConstructor
public class RefreshTokenResult {

    private ResponseCookie accessCookie;
    private ResponseCookie refreshCookie;
}