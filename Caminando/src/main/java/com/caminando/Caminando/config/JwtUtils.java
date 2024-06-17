package com.caminando.Caminando.config;

import com.caminando.Caminando.businesslayer.services.security.SecurityUserDetails;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.io.Decoders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtils {

	@Value("${jwt.key}")
	private String securityKey;

	@Value("${jwt.expirationMs}")
	private long expirationMs;

	private SecretKey getSigningKey() {
		byte[] keyBytes = Decoders.BASE64.decode(securityKey);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	public String generateToken(Authentication auth) {
		SecurityUserDetails user = (SecurityUserDetails) auth.getPrincipal();
		return Jwts.builder()
				.setSubject(user.getUsername())
				.setIssuedAt(new Date())
				.setIssuer("MySpringApplication")
				.setExpiration(new Date(new Date().getTime() + expirationMs))
				.signWith(getSigningKey())
				.compact();
	}

	public boolean isTokenValid(String token) {
		try {
			Jwts.parser()
					.setSigningKey(getSigningKey())
					.requireIssuer("MySpringApplication")
					.build()
					.parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			// Aggiungi log dettagliati per migliorare il debugging
			return false;
		}
	}

	public String getUsernameFromToken(String token) {
		return Jwts.parser()
				.setSigningKey(getSigningKey())
				.build()
				.parseClaimsJws(token)
				.getBody()
				.getSubject();
	}
}
