package com.eccomerce.nb_eccomerce.security.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.util.Set;

@Getter
public class SignupRequest {
    @NotBlank(message = "Username must not be blank")
    @Size(min = 5,max = 15,message = "Username must be between 5 to 15 characters")
    private String username;

    @NotBlank
    @Size(min=10,max=40,message = "Password size must be between 10 to 40 characters")
    private String password;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    private Set<String>roles;
}
