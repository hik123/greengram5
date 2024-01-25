package com.green.greengram4.security;


import com.green.greengram4.common.MyUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFaCade {
    public MyUserDetails getLoginUser() {

        return (MyUserDetails)SecurityContextHolder
                                .getContext()
                                .getAuthentication() //비로그인 상태 >> null이 넘어오거나 0이넘어오면 ㅊ처리
                                .getPrincipal();

    }

    public int getLoginUserPk() {

        return getLoginUser().getMyPrincipal().getIuser();
    }
}
