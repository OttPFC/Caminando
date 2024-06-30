package com.caminando.Caminando.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

	@Bean
	public PasswordEncoder stdPasswordEncoder() {
		return new BCryptPasswordEncoder(11);
	}

	@Bean
	public AuthTokenFilter authenticationJwtToken() {
		return new AuthTokenFilter();
	}

	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity http,
													   PasswordEncoder passwordEncoder,
													   UserDetailsService userDetailsService) throws Exception {
		AuthenticationManagerBuilder authenticationManagerBuilder =
				http.getSharedObject(AuthenticationManagerBuilder.class);

		authenticationManagerBuilder
				.userDetailsService(userDetailsService)
				.passwordEncoder(passwordEncoder);

		return authenticationManagerBuilder.build();
	}

	@Bean
	public SecurityFilterChain web(HttpSecurity http) throws Exception {
		http
				.csrf(AbstractHttpConfigurer::disable)
				.cors(cors -> cors.configurationSource(corsConfigurationSource()))
				.authorizeHttpRequests(authorize -> authorize

						.requestMatchers(HttpMethod.GET, "/api/user/{id}").permitAll()
						.requestMatchers(HttpMethod.GET, "/api/trips").authenticated()
						.requestMatchers(HttpMethod.GET, "/api/trips/{id}").authenticated()
						//ADMIN
						.requestMatchers(HttpMethod.GET, "/**").hasAuthority("ADMIN")
						// ------------------------------------------------------------------------
						.requestMatchers(HttpMethod.POST, "/api/user/login").permitAll()
						.requestMatchers(HttpMethod.POST, "/api/user").permitAll()
						.requestMatchers(HttpMethod.POST, "/api/trips").authenticated()
						//ITINERARY
						.requestMatchers(HttpMethod.POST, "/api/itinerary").hasAuthority("ADMIN")
						.requestMatchers(HttpMethod.POST, "/api/itinerary/{id}").hasAuthority("ADMIN")

						//TO-DO
						.requestMatchers(HttpMethod.POST, "/api/todo").hasAuthority("ADMIN")
						.requestMatchers(HttpMethod.POST, "/api/todo/{id}").hasAuthority("ADMIN")
						//RESTAURANT
						.requestMatchers(HttpMethod.POST, "/api/restaurant").hasAuthority("ADMIN")
						.requestMatchers(HttpMethod.POST, "/api/restaurant/{id}").hasAuthority("ADMIN")
						//FOOD
						.requestMatchers(HttpMethod.POST, "/api/food").hasAuthority("ADMIN")
						.requestMatchers(HttpMethod.POST, "/api/food/{id}").hasAuthority("ADMIN")
						//QUICK
						.requestMatchers(HttpMethod.POST, "/api/quick-fact").hasAuthority("ADMIN")
						.requestMatchers(HttpMethod.POST, "/api/quick-fact/{id}").hasAuthority("ADMIN")
						//PLACE
						.requestMatchers(HttpMethod.POST, "/api/place-to-stay").hasAuthority("ADMIN")
						.requestMatchers(HttpMethod.POST, "/api/place-to-stay/{id}").hasAuthority("ADMIN")
						//---------------------------------------------------------------------------

						//ITINERARY
						.requestMatchers(HttpMethod.PUT, "/api/itinerary").hasAuthority("ADMIN")
						.requestMatchers(HttpMethod.PUT, "/api/itinerary/{id}").hasAuthority("ADMIN")
						//TO-DO
						.requestMatchers(HttpMethod.PUT, "/api/todo").hasAuthority("ADMIN")
						.requestMatchers(HttpMethod.PUT, "/api/todo/{id}").hasAuthority("ADMIN")
						//RESTAURANT
						.requestMatchers(HttpMethod.PUT, "/api/restaurant").hasAuthority("ADMIN")
						.requestMatchers(HttpMethod.PUT, "/api/restaurant/{id}").hasAuthority("ADMIN")
						//FOOD
						.requestMatchers(HttpMethod.PUT, "/api/food").hasAuthority("ADMIN")
						.requestMatchers(HttpMethod.PUT, "/api/food/{id}").hasAuthority("ADMIN")
						//QUICK
						.requestMatchers(HttpMethod.PUT, "/api/quick-fact").hasAuthority("ADMIN")
						.requestMatchers(HttpMethod.PUT, "/api/quick-fact/{id}").hasAuthority("ADMIN")
						//PLACE
						.requestMatchers(HttpMethod.PUT, "/api/place-to-stay").hasAuthority("ADMIN")
						.requestMatchers(HttpMethod.PUT, "/api/place-to-stay/{id}").hasAuthority("ADMIN")
						//ADMIN
						.requestMatchers(HttpMethod.PUT, "/**").hasAuthority("ADMIN")
						.requestMatchers(HttpMethod.PUT, "/api/user/{id}").permitAll()
						//---------------------------------------------------------------------------

						//ITINERARY
						.requestMatchers(HttpMethod.PATCH, "/api/itinerary").hasAuthority("ADMIN")
						.requestMatchers(HttpMethod.PATCH, "/api/itinerary/{id}").hasAuthority("ADMIN")
						//ADMIN
						.requestMatchers(HttpMethod.PATCH, "/**").hasAuthority("ADMIN")

						.requestMatchers(HttpMethod.DELETE, "/**").hasAuthority("ADMIN")
						.anyRequest().authenticated()
				)
				.httpBasic(Customizer.withDefaults())
				.sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.addFilterBefore(authenticationJwtToken(), UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.addAllowedOrigin("http://localhost:4200");
		config.setAllowedHeaders(Arrays.asList(
				HttpHeaders.AUTHORIZATION,
				HttpHeaders.CONTENT_TYPE,
				HttpHeaders.ACCEPT));
		config.setAllowedMethods(Arrays.asList(
				HttpMethod.GET.name(),
				HttpMethod.POST.name(),
				HttpMethod.PUT.name(),
				HttpMethod.PATCH.name(),
                HttpMethod.OPTIONS.name(),
                HttpMethod.HEAD.name(),
                HttpMethod.TRACE.name(),
				HttpMethod.DELETE.name()));
		config.setMaxAge(3600L);
		source.registerCorsConfiguration("/**", config);
		return source;
	}
}
