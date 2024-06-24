package com.caminando.Caminando.presentationlayer.utility;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;

public class JwtUtil {

    private final SecretKey secretKey;
    private final String base64Key;
    private final long expirationMs;

    public JwtUtil(String securityKey, long expirationMs) {
        byte[] keyBytes = Decoders.BASE64.decode(securityKey);
        if (keyBytes.length < 32) {
            throw new IllegalArgumentException("The key must be at least 256 bits");
        }
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
        this.base64Key = Encoders.BASE64.encode(secretKey.getEncoded());
        this.expirationMs = expirationMs;
    }

    public String createJwt(String subject) {
        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims extractClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isTokenValid(String token, String username) {
        Claims claims = extractClaims(token);
        return claims.getSubject().equals(username) && !isTokenExpired(claims);
    }

    private boolean isTokenExpired(Claims claims) {
        return claims.getExpiration().before(new Date());
    }

    public boolean isTokenExpired(String token) {
        Claims claims = extractClaims(token);
        return isTokenExpired(claims);
    }

    public String getUsernameFromToken(String token) {
        return extractClaims(token).getSubject();
    }
}
