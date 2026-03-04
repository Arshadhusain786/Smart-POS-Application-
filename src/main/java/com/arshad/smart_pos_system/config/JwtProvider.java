package com.arshad.smart_pos_system.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
public class JwtProvider {

    private final SecretKey key =
            Keys.hmacShaKeyFor(JwtConstant.JWT_SECRET.getBytes());

    // =============================
    // Generate JWT
    // =============================
    public String generateToken(Authentication authentication) {

        Collection<? extends GrantedAuthority> authorities =
                authentication.getAuthorities();

        String roles = populateAuthorities(authorities);

        return Jwts.builder()
                .subject(authentication.getName()) // username/email
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 86400000)) // 24 hours
                .claim("authorities", roles)
                .signWith(key)
                .compact();
    }

    // =============================
    // Extract Username (Email)
    // =============================
    public String getUsernameFromJWTToken(String token) {

        return getClaims(token).getSubject();
    }

    // =============================
    // Extract Authorities
    // =============================
    public String extractAuthorities(String token) {

        return getClaims(token).get("authorities", String.class);
    }

    // =============================
    // Extract Claims
    // =============================
    private Claims getClaims(String token) {

        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // =============================
    // Validate Token
    // =============================
    public boolean validateToken(String token) {

        try {
            getClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // =============================
    // Convert Authorities
    // =============================
    private String populateAuthorities(Collection<? extends GrantedAuthority> authorities) {

        Set<String> auths = new HashSet<>();

        for (GrantedAuthority authority : authorities) {
            auths.add(authority.getAuthority());
        }

        return String.join(",", auths);
    }
}