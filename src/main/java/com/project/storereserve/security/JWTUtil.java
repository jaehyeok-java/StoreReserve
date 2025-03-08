package com.project.storereserve.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JWTUtil {

    @Value("${jwt.secret}")
    private String secretKey;

    private Key SECRET_KEY;

    // 생성자로 초기화시 실행시점의 secretKey 값이 Null 이므로 오류 -> Bean 생성이 끝난후 @PostConstruct 이용해야함.
    @PostConstruct
    public void init() {
        this.SECRET_KEY = Keys.hmacShaKeyFor(secretKey.getBytes());
    }


    // JWT 토큰 생성
    public String generateToken(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .claim("role", authentication.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.joining(", "))) // [ ] 제거
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10시간 유효
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    // JWT 토큰에서 사용자 이메일 추출
    public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }

    // JWT 토큰 유효성 검증
    public boolean isTokenExpired(String token) {
        return getClaims(token).getExpiration().before(new Date());
    }

    // 토큰에서 역할(role) 추출
    public String extractRole(String token) {
        return (String) getClaims(token).get("role");
    }

    // 공통 Claims 추출 메서드
    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
