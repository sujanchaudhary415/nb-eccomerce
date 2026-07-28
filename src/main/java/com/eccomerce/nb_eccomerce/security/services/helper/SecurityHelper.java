package com.eccomerce.nb_eccomerce.security.services.helper;

import org.springframework.stereotype.Component;

@Component
public class SecurityHelper {

    public String normalize(String username){
        return username.trim().replaceAll("\\s+"," ");
    }

    public String normalizeEmail(String email){
        return email.trim().toLowerCase();
    }
}
