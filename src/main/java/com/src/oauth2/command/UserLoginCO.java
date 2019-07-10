package com.src.oauth2.command;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class UserLoginCO {
	@NotBlank
	@Email
	private String email;

	@NotBlank
	private String password;
}
