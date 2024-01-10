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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.Key;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final ObjectMapper om;
    private final AppProperties appProperties;
    private Key key;

    @PostConstruct
    public void init() {
        log.info("secret: {}", appProperties.getJwt().getSecret());
        byte[] keyBytes = Decoders.BASE64.decode(appProperties.getJwt().getSecret());
        log.info("keyBytes: {}", keyBytes);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateAccessToken(MyPrincipal principal) {
        return generateToken(principal, appProperties.getJwt().getAccessTokenExpiry());
    }

    public String generateRefreshToken(MyPrincipal principal) {
        return generateToken(principal, appProperties.getJwt().getRefreshTokenExpiry());
    }

    private String generateToken(MyPrincipal principal, long tokenValidMs) {
        return Jwts.builder()
                .claims(createClaims(principal))
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + tokenValidMs))
                .signWith(this.key)
                .compact();
    }

    private Claims createClaims(MyPrincipal principal) {
        try {
            String json = om.writeValueAsString(principal);
            return Jwts.claims()
                    .add("user", json)
                    .build();
        } catch (Exception e) {
            return null;
        }
    }

    public String resolveToken(HttpServletRequest req) {
        String auth = req.getHeader(appProperties.getJwt().getHeaderSchemeName());
        if(auth == null) { return null; }

        //Bearer Askladjflaksjfw309jasdklfj
        if(auth.startsWith(appProperties.getJwt().getTokenType())) {
            return auth.substring(appProperties.getJwt().getTokenType().length()).trim();
        }
        return null;
        //return auth == null ? null : auth.startsWith();
    }

    public boolean isValidateToken(String token) {
        try {
            //만료기간 현재시간보다 전이면 false, 현재시간이 만료기간보다 후이면 false
            //만료기간 현재시간보다 후이면 true, 현재시간이 만료기간보다 전이면 true
            return !getAllClaims(token).getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    private Claims getAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
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
            String json = (String)claims.get("user");
            MyPrincipal myPrincipal = om.readValue(json, MyPrincipal.class);
            return MyUserDetails.builder()
                    .myPrincipal(myPrincipal)
                    .build();
        } catch (Exception e) {
            return null;
        }
    }
}
