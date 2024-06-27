package com.caminando.Caminando.config;

import com.caminando.Caminando.businesslayer.services.security.ApplicationUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
public class AuthTokenFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private ApplicationUserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request,
									HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		try {
			String header = request.getHeader("Authorization");
			log.info("Authorization Header: {}", header);
			if (header != null && header.startsWith("Bearer ")) {
				String token = extractToken(header);
				log.info("JWT Token: {}", token);
				if (jwtUtils.isTokenValid(token)) {
					String username = jwtUtils.getUsernameFromToken(token);
					log.info("Username from Token: {}", username);
					UserDetails userDetails = userDetailsService.loadUserByUsername(username);
					log.info("UserDetails: {}", userDetails);
					UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
					authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authentication);
					log.info("Authentication set for user: {}", username);
				} else {
					log.warn("Invalid JWT token");
				}
			} else {
				log.warn("No JWT token found in request headers");
			}
		} catch (Exception e) {
			log.error("Cannot set user authentication: {}", e);
		}
		filterChain.doFilter(request, response);
	}

	private String extractToken(String header) {
		// Split and return the first token if multiple tokens are present
		return header.split(",")[0].substring(7).trim();
	}
}
