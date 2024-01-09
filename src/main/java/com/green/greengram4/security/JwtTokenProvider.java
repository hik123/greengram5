package com.green.greengram4.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.greengram4.common.AppProperties;
import com.green.greengram4.common.MyUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.Key;
import java.util.Date;

@Slf4j
@Component //역할이 없는 그냥 빈등록
@RequiredArgsConstructor
public class JwtTokenProvider {
    //@Value("${springboot.jwt.secret}")

    private final ObjectMapper om; //
    private final AppProperties appProperties;
    private Key key;

    /*
    public JwtTokenProvider(@Value("${springboot.jwt.secret}") String secret
                        , @Value("${springboot.jwt.header-scheme-name}") String headerSchemeName
                        , @Value("${springboot.jwt.token-type") String tokenType
    ) {
        this.secret = secret;
        this.headerSchemeName = headerSchemeName;
       this.tokenType = tokenType;
    }

     */


            // Component빈등록 안되면 PostConstruct작동안함, 호출하기전에 di 부터 이뤄짐 그러고 나서 PostConstruct 호출당함
    @PostConstruct // 빈등록 되고 di 받을게 있으면 받고난 후 호출  // 빈등록 >> 스프링 컨테이너에 대리로 일 시키는것 // 메소드 호출하고싶을때, 그때하고싶을 일을 적음
    public void init() {
        log.info("secret : {}", appProperties.getJwt().getSecret());
        byte[] keyBytes = Decoders.BASE64.decode(appProperties.getJwt().getSecret());
        log.info("keyBytes: {}", keyBytes);
        this.key = Keys.hmacShaKeyFor(keyBytes);

    }

    public String generateAccessToken(MyPrincipal principal) {
        return generateToken(principal, appProperties.getJwt().getAccessTokenExPiry());
    }
    public String generateRefreshToken(MyPrincipal principal) {
        return generateToken(principal, appProperties.getJwt().getRefreshTokenExPiry());
    }

    private String generateToken(MyPrincipal principal, long tokenValidMs) {
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
        try {
            String json = om.writeValueAsString(principal);

            return Jwts.claims()
                    .add("user", json)
                    .build();
        } catch(Exception e) {
            return null;
        }
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = getUserDetailsFromToken(token);

        return userDetails == null
                ? null
                : new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    private UserDetails getUserDetailsFromToken(String token) {
        try {
            Claims claims = getAllClaims(token);
            String json = (String) claims.get("user");
            MyPrincipal myPrincipal = om.readValue(json, MyPrincipal.class);
            return MyUserDetails.builder()
                    .myPrincipal(myPrincipal)
                    .build();
        } catch(Exception e) {
            return null;
        }

    }

    //
    public String resolveToken(HttpServletRequest req) {

        String auth = req.getHeader(appProperties.getJwt().getHeaderSchemeName()); //헤더에 담겨있는것중에 headerSchemeName키값으로 auth밸류 달라는것
        if(auth == null) { return null; }

        //Bearer Asdfaewgwgeweg24sg          //subString << 문자열 자르기 인자 하나 보내면 거기서부터 끝까지
        if(auth.startsWith(appProperties.getJwt().getTokenType())) {
            return auth.substring(appProperties.getJwt().getTokenType().length()).trim();   // trim() << 문자열 앞뒤 공백제거
        }
        return null;
    }

    public boolean isValidateToken(String token) {
        try {
            //만료기간이 현재시간보다 전이면 false 만료기간이 현재시간보다 후면 true
            //만료기간이 현재시간보다 후이면 true, 현재시간이 만료기간보다 전이면 true
            return !getAllClaims(token).getExpiration().before(new Date()); // 만료됐는지 체크
        } catch(Exception e) {
            return false;
        }
    }
    private Claims getAllClaims(String token) {
        return Jwts.parser().setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
