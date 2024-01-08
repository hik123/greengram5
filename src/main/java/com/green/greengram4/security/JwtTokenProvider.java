package com.green.greengram4.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.Key;
import java.util.Date;

@Slf4j
@Component //역할이 없는 그냥 빈등록
public class JwtTokenProvider {
    //@Value("${springboot.jwt.secret}")
    private final String secret;
    private final String headerSchemeName;
    private final String tokenType;
    private Key key;

    public JwtTokenProvider(@Value("${springboot.jwt.secret}") String secret
                        , @Value("${springboot.jwt.header-scheme-name}") String headerSchemeName
                        , @Value("${springboot.jwt.token-type") String tokenType
    ) {
        this.secret = secret;
        this.headerSchemeName = headerSchemeName;
        this.tokenType = tokenType;
    }
            // Component빈등록 안되면 PostConstruct작동안함, 호출하기전에 di 부터 이뤄짐 그러고 나서 PostConstruct 호출당함
    @PostConstruct // 빈등록 되고 di 받을게 있으면 받고난 후 호출  // 빈등록 >> 스프링 컨테이너에 대리로 일 시키는것 // 메소드 호출하고싶을때, 그때하고싶을 일을 적음
    public void init() {
        log.info("secret : {}", secret);
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(MyPrincipal principal, long tokenValidMs) {
        //엑세스 토큰, 리프레쉬 토큰 살아있는 기간이 다름
        Date now = new Date();
        return Jwts.builder()
                //.issuedAt(now)
                .claims(createClaims(principal))
                .issuedAt(new Date(System.currentTimeMillis())) //발행날짜? 언제 이토큰을 만들었냐
                .expiration(new Date(System.currentTimeMillis() + tokenValidMs))
                .signWith(this.key)
                .compact();

    } //jwt 토큰은 그대로 가져다 사용하면됨

    private Claims createClaims(MyPrincipal principal) {
        return Jwts.claims()
                .add("iuser", principal.getIuser())
                .build();
    }

    //
    public String resolveToken(HttpServletRequest req) {
        String auth = req.getHeader(headerSchemeName); //헤더에 담겨있는것중에 headerSchemeName키값으로 auth밸류 달라는것
        if(auth == null) { return null; }

        if(auth.startsWith(tokenType)) {
            return auth.substring(tokenType.length()).trim();
        }
        return null;
    }
}
