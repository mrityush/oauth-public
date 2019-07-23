package com.src.oauth2.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.src.oauth2.domain.entity.CommonDataEntity;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "users", uniqueConstraints = {
		@UniqueConstraint(columnNames = "email")
})
@Data
public class User extends CommonDataEntity implements Serializable {
	public User() {
		super();
	}


	@Column(nullable = false)
	private String firstName;

	@Column
	private String lastName;

	@Email
	@Column(nullable = false)
	private String email;

	private String imageUrl;

	@Column(nullable = false)
	private Boolean emailVerified = false;

	@JsonIgnore
	private String password;

	@NotNull
	@Enumerated(EnumType.STRING)
	private AuthProvider provider;

	@Column
	private String providerId;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private UserAuthority authorities;
}
