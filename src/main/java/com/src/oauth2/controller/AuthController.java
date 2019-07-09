package com.src.oauth2.controller;

import com.src.oauth2.domain.AuthProvider;
import com.src.oauth2.domain.User;
import com.src.oauth2.domain.UserAuthority;
import com.src.oauth2.exception.BadRequestException;
import com.src.oauth2.payload.ApiResponse;
import com.src.oauth2.payload.AuthResponse;
import com.src.oauth2.payload.LoginRequest;
import com.src.oauth2.payload.SignUpRequest;
import com.src.oauth2.repositories.UserRepository;
import com.src.oauth2.security.CurrentUser;
import com.src.oauth2.security.TokenProvider;
import com.src.oauth2.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.HashSet;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private TokenProvider tokenProvider;

	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						loginRequest.getEmail(),
						loginRequest.getPassword()
				)
		);

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String token = tokenProvider.createToken(authentication);
		return ResponseEntity.ok(new AuthResponse(token));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			throw new BadRequestException("Email address already in use.");
		}

		// Creating user's account
		User user = new User();
		user.setName(signUpRequest.getName());
		user.setEmail(signUpRequest.getEmail());
		user.setPassword(signUpRequest.getPassword());
		user.setProvider(AuthProvider.local);
		user.setAuthorities(new HashSet<UserAuthority>() {{
			add(new UserAuthority("ROLE_USER"));
		}});
		user.setPassword(passwordEncoder.encode(user.getPassword()));

		User result = userRepository.save(user);

		URI location = ServletUriComponentsBuilder
				.fromCurrentContextPath().path("/user/me")
				.buildAndExpand(result.getId()).toUri();

		return ResponseEntity.created(location)
				.body(new ApiResponse(true, "User registered successfully@"));
	}

	@PostMapping("/enticate")
	public ResponseEntity<?> authenticate(@CurrentUser UserPrincipal userPrincipal) {
//	public  ResponseEntity<?> authenticate(@RequestHeader("authorization") String authorization){
		if (userPrincipal == null)
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ApiResponse(false, "User not Found or not authenticated."));
		// todo may need to add user information / role in response
		return ResponseEntity.ok()
				.body(new ApiResponse(true, "will see"));
	}

}
