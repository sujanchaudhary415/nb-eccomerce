package com.eccomerce.nb_eccomerce.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserRequest {
    @NotBlank(message = "Username must not be blank ")
    @Size(min = 5,max=20)
    private String userName;

    @NotBlank(message = "Email must not be blank")
    @Email
    @Size(max=50)
    private String email;

    @NotBlank(message = "Password must not be blank")
    @Size(max = 120)
    private String password;
}
