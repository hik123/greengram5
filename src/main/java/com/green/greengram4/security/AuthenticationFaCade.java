package com.green.greengram4.security;


import com.green.greengram4.common.MyUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFaCade {
    public MyUserDetails getLoginUser() {

        return (MyUserDetails)SecurityContextHolder
                                .getContext()
                                .getAuthentication()
                                .getPrincipal();

    }

    public int getLoginUserPk() {

        return getLoginUser().getMyPrincipal().getIuser();
    }
}
