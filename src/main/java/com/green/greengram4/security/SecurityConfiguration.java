package com.green.greengram4.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration { //시큐리티 세팅

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean // 스프링컨테이너가 호출함 어느 세팅에 쓰인다는것 ,
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(http -> http.disable()) //람다식
                .formLogin(form -> form.disable())
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth.requestMatchers(
                         "/api/feed"
                        ,"/api/feed/comment"
                        ,"/api/dm"
                        ,"/api/dm/msg"
                        ).authenticated() // 위 인증한 사람들만 사용가능
                        .requestMatchers(HttpMethod.POST, "/api/user/signout" //post 제외한 주소들은 허가하겠다
                                                        , "/api/user/follow"
                        ).authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/user").authenticated() //<< 주소로 들어오는 get은 로그인처리되야됨
                        .requestMatchers(HttpMethod.PATCH, "/api/user/pic").authenticated() //<< 주소로 들어오는 patch은 로그인처리되야됨
                        .requestMatchers(HttpMethod.GET, "/api/feed/fav").hasAnyRole("ADMIN") //<< 주소로 들어오는  //유저는 fav 못씀 어드민이면 fav 사용가능
                        .anyRequest().permitAll()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class) // // add기존거 두고 추가 // set 기존꺼 싹다 덮어씀 지우고 추가
                .exceptionHandling(except -> {
                    except.authenticationEntryPoint(new JwtAuthenticationEntryPoint())
                            .accessDeniedHandler(new JwtAccessDeniedHandler());
                })
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
