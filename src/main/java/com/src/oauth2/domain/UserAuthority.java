package com.src.oauth2.domain;


import com.src.oauth2.domain.entity.CommonDataEntity;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

/**
 * Created by Priya on 11/3/17.
 */
@Entity
@Table(name = "authorities")
@Data
public class UserAuthority extends CommonDataEntity implements GrantedAuthority {

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(referencedColumnName = "username", name = "username")
	private RegisteredUser user;
	private String authority;// TODO can have multiple authorites but right now not handling..

	public UserAuthority() {
	}

	public UserAuthority(RegisteredUser user, String name) {
		this.user = user;
		this.authority = name;
	}

	@Override
	public String getAuthority() {
		return authority;
	}

	public RegisteredUser getUser() {
		return user;
	}
}
