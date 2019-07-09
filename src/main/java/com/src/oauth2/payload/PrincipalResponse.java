package com.src.oauth2.payload;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Data
public class PrincipalResponse {
	private Long id;
	private Collection<? extends GrantedAuthority> authorities;
}
