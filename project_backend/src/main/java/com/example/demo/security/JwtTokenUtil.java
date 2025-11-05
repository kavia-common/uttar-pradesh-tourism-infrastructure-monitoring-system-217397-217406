package com.example.demo.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Utility for creating and validating JWT tokens.
 */
@Component
public class JwtTokenUtil {

    private final SecretKey secretKey;
    private final long jwtExpirationMs;
    private final long refreshExpirationMs;

    public JwtTokenUtil(
            @Value("${APP_JWT_SECRET:dev-secret-change-me}") String base64Secret,
            @Value("${APP_JWT_EXPIRATION_MS:3600000}") long jwtExpirationMs,
            @Value("${APP_JWT_REFRESH_EXPIRATION_MS:1209600000}") long refreshExpirationMs) {
        // base64 or raw support
        byte[] keyBytes;
        try {
            keyBytes = Decoders.BASE64.decode(base64Secret);
        } catch (Exception ex) {
            keyBytes = base64Secret.getBytes();
        }
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
        this.jwtExpirationMs = jwtExpirationMs;
        this.refreshExpirationMs = refreshExpirationMs;
    }

    public String generateAccessToken(UserDetails userDetails, Map<String, Object> extraClaims) {
        Map<String, Object> claims = new HashMap<>(extraClaims == null ? Map.of() : extraClaims);
        claims.put("sub", userDetails.getUsername());
        claims.put("roles", userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.toList()));

        Date now = new Date();
        Date expiry = new Date(now.getTime() + jwtExpirationMs);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken(String username) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + refreshExpirationMs);
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .claim("typ", "refresh")
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        String username = getUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    public String getUsername(String token) {
        Claims claims = parseAllClaims(token);
        String sub = claims.get("sub", String.class);
        if (sub != null) return sub;
        return claims.getSubject();
    }

    public boolean isRefreshToken(String token) {
        Claims claims = parseAllClaims(token);
        String typ = claims.get("typ", String.class);
        return "refresh".equalsIgnoreCase(typ);
    }

    public boolean isTokenExpired(String token) {
        return parseAllClaims(token).getExpiration().before(new Date());
    }

    public Claims parseAllClaims(String token) throws JwtException {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
