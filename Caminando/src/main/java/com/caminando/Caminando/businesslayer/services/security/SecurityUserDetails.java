package com.caminando.Caminando.businesslayer.services.security;

import com.caminando.Caminando.datalayer.entities.travel.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@Builder(setterPrefix = "with")
public class SecurityUserDetails implements UserDetails {
	private static final long serialVersionUID = 1L;

	private Collection<? extends GrantedAuthority> authorities;
	private String password;
	private String username;
	private boolean accountNonExpired;
	private boolean accountNonLocked;
	private boolean credentialsNonExpired;
	private boolean enabled;

	public static SecurityUserDetails build(User user) {
		List<GrantedAuthority> authorities = user.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getRoleType()))
				.collect(Collectors.toList());
		return SecurityUserDetails.builder()
				.withUsername(user.getUsername())
				.withPassword(user.getPassword())
				.withAuthorities(authorities)
				.withEnabled(user.isEnabled())
				.withAccountNonExpired(true)
				.withAccountNonLocked(true)
				.withCredentialsNonExpired(true)
				.build();
	}

	@Override
	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}
}
