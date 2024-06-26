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
	private JwtUtils jwt;

	@Autowired
	private ApplicationUserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request,
									HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		try {
			log.info("Processing AuthTokenFilter");

			String header = request.getHeader("Authorization");
			if (header != null && header.startsWith("Bearer ")) {
				String token = header.substring(7);
				log.info("Token: {}", token);
				String email = jwt.getUsernameFromToken(token);
				log.info("Username: {}", email);
				UserDetails details = userDetailsService.loadUserByUsername(email);
				log.info("Details: {}", details);

				if (jwt.isTokenValid(token)) {
					UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(details, null, details.getAuthorities());
					auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(auth);
				} else {
					log.warn("Invalid JWT token");
				}
			}
		} catch (Exception e) {
			log.error("Exception in auth filter", e);
		}
		filterChain.doFilter(request, response);
	}

}
