package com.eccomerce.nb_eccomerce.security.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class AuthResponse {
    private final Long userId;
    private final String username;
    private final String email;
    private final List<String> roles;
}
