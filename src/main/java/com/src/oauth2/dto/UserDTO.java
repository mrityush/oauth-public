package com.src.oauth2.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Email;
import java.util.Set;

/**
 * Created by mj on 25/5/16.
 */
@Component
@Data
public class UserDTO {

	private Long id;
	private String firstName;
	private String lastName;
	@Email
	private String email;
	private String imageUrl;
	private Boolean emailVerified = false;
	@JsonIgnore
	private String password;
	private String provider;
	private String providerId;
	private Set<UserAuthorityDTO> authorities;
}
