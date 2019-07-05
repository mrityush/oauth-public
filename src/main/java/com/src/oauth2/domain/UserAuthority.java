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

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(referencedColumnName = "email", name = "email")
	private User user;
	@Column
	private String authority;

	public UserAuthority() {
	}

	public UserAuthority(String name) {
		this.authority = name;
	}

	@Override
	public String getAuthority() {
		return authority;
	}

}
