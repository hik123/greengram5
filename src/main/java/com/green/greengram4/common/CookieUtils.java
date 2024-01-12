package com.green.greengram4.common;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import org.springframework.stereotype.Component;

import static org.springframework.web.util.WebUtils.getCookie;

@Component
public class CookieUtils {
    public Cookie getCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();

        if(cookies != null && cookies.length > 0) {
            for(Cookie cookie : cookies) {
                if(name.equals(cookie.getName())) {
                    return cookie;
                }
            }
        }
        return null;
    }

    public void setCookie(HttpServletResponse response, String name, String value, int maxAge) { //maxAge 초값
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");   //모든주소값에서 쿠키를 사용할수있다
        cookie.setHttpOnly(true);
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    public void deleteCookie(HttpServletResponse response, String name) {
        Cookie cookie = new Cookie(name, null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
