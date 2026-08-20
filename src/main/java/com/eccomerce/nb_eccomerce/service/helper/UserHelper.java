package com.eccomerce.nb_eccomerce.service.helper;

import com.eccomerce.nb_eccomerce.security.services.Impl.UserDetailsImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UserHelper {

    public Long getAuthenticatedUserId(){
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        UserDetailsImpl userDetails =
                (UserDetailsImpl) authentication.getPrincipal();

        return userDetails.getId();
    }
}
