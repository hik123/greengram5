package com.green.greengram4.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
//@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(http -> http.disable()) // 리소스 아끼기 위해 끔
                .formLogin(from -> from.disable())
                .csrf(csrf -> csrf.disable()) // csrf 끔 / 우리가 만든 화면이 없기때문에, 프론트에 데이터만 보내기때문에 끔
                .authorizeHttpRequests(auth -> auth.requestMatchers("/api/user/signin"
                                                                    ,"/api/user/signup"
                                                                    ,"/error"
                                                                    ,"/err"
                                                                    ,"/"
                                                                    ,"/index.html"
                                                                    ,"/static/**"
                                                                    ,"/swagger.html"
                                                                    ,"/swagger-ui/**"
                                                                    ,"/v3/api-docs/**"
                                        ).permitAll() // permitAll무사통과 시켜줌
                                .anyRequest().authenticated()
                        /*
                        .requestMatchers(HttpMethod.GET, "sign-api/refresh-token").permitAll()
                        .requestMatchers(HttpMethod.GET, "product/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "product/**").permitAll()
                        .requestMatchers("**exception**").permitAll()
                        */
                        //.anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(except -> {
                    except.authenticationEntryPoint(new JwtAuthenticationEntryPoint())
                            .accessDeniedHandler(new JwtAccessDeniedHandler());
                })
                .build();                               //sessionManagement <<세션을 사용하지않겠다  // 세션의 단점 ex) 동접자 많을때 메모리 과부하
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();

    }
}
