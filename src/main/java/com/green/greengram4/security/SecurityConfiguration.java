package com.green.greengram4.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
                .authorizeHttpRequests(auth -> auth.requestMatchers("/api/user/signin"
                                                                    , "/api/user/signup"
                                                                    , "/api/user/refresh-token"
                                                                    , "/error"
                                                                    , "/err"
                                                                    , "/"
                                                                    , "/profile/**"
                                                                    , "/pic/**"        // 슬래시, /** 빠졌는지 확인
                                                                    , "/feed/**"
                                                                    , "/feed"
                                                                    , "/signin"
                                                                    , "/signup"
                                                                    , "/fimg/**"
                                                                    , "/css/**"
                                                                    , "/static/**"
                                                                    , "/index.html"
                                                                    , "/static/**"
                                                                    , "/swagger.html"
                                                                    , "/swagger-ui/**"
                                                                    , "/v3/api-docs/**"
                                        ).permitAll()
                        .anyRequest().authenticated()
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
