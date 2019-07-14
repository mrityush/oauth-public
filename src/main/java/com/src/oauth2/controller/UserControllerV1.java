package com.src.oauth2.controller;

import com.src.oauth2.command.UserCO;
import com.src.oauth2.command.UserLoginCO;
import com.src.oauth2.domain.User;
import com.src.oauth2.dto.UserDTO;
import com.src.oauth2.exception.ResourceNotFoundException;
import com.src.oauth2.repositories.UserRepository;
import com.src.oauth2.security.CurrentUser;
import com.src.oauth2.security.UserPrincipal;
import com.src.oauth2.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserControllerV1 {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserService userService;

	@GetMapping("/user/me")
	@PreAuthorize("hasRole('USER')")
	public User getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
		return userRepository.findById(userPrincipal.getId())
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
	}

	@PostMapping("/v1/register")
	public UserDTO registerUser(@RequestBody UserCO userCO) {
		return userService.registerUser(userCO);
	}

	@PostMapping("/v1/login")
	public ResponseEntity<?> loginUser(@RequestBody UserLoginCO userLoginCO) {
		return userService.loginUser(userLoginCO);
	}
}
