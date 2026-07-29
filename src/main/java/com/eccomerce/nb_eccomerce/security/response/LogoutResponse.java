package com.eccomerce.nb_eccomerce.security.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.ResponseCookie;

@Getter
@AllArgsConstructor
public class LogoutResponse {

    private final ResponseCookie accessCookie;
    private final ResponseCookie refreshCookie;
}