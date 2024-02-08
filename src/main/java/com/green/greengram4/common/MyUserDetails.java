package com.green.greengram4.common;


import com.green.greengram4.security.MyPrincipal;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.text.CollationElementIterator;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@Builder
public class MyUserDetails implements UserDetails, OAuth2User { //권한처리

    private MyPrincipal myPrincipal;
    private Map<String, Object> attributes;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(myPrincipal == null) {
            return null;
        }
        return this.myPrincipal.getRoles().stream()
                //.map(SimpleGrantedAuthority::new) // ROLE_ADMIN 가공이 없다면 이렇게 써도됨
                //.map(() -> new SimpleGrantedAuthority())          //fuction 특징 1번째:파라미터도 있고 2번째:리턴타입도 있음
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());      //권한 1개있으면 1개짜리 리턴 // collection list map ..컬렉션 사용방법을 통일시키기위해 stream사용
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
