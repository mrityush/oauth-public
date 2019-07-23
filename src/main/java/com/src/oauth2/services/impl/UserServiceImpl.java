package com.src.oauth2.services.impl;

import com.src.oauth2.command.UserCO;
import com.src.oauth2.command.UserLoginCO;
import com.src.oauth2.controller.AuthControllerV1;
import com.src.oauth2.datachannels.HttpChannel;
import com.src.oauth2.domain.AuthProvider;
import com.src.oauth2.domain.RegisteredUser;
import com.src.oauth2.domain.User;
import com.src.oauth2.domain.UserAuthority;
import com.src.oauth2.dto.UserDTO;
import com.src.oauth2.payload.LoginRequest;
import com.src.oauth2.repositories.UserRepository;
import com.src.oauth2.services.interfaces.UserService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * oauth2
 * Mrityunjaya@infoedge
 * 27/6/19 4:21 PM
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepository userRepository;
	@Autowired
	ModelMapper modelMapper;
	@Autowired
	AuthControllerV1 authControllerV1;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private HttpChannel httpChannel;
	@Autowired
	private MongoOperations mongoOperations2;

	@Override
	public void sendWelcomeEmail(UserAuthority userAuthority) {

	}

	@Override
	public String getReferralCodeForUser(RegisteredUser user) {
		return null;
	}

	@Override
	@Transactional
	public UserDTO registerUserAndSendToProfileService(UserCO userCO) {
		userCO.setPassword(passwordEncoder.encode(userCO.getPassword()));
		User user = modelMapper.map(userCO, User.class);
		String userAuthorityStr = getUserAuthoritesStr(userCO);
		user.setAuthorities(new UserAuthority(userAuthorityStr, user));
		user.setEmailVerified(false);
		user.setVersion(0L);
		user.setProvider(AuthProvider.local);
		user = userRepository.save(user);
		userCO.setId(user.getId());
		// todo ... in case the userCO is not sent to queue,
		//  we will have to persist the userCO to a nosql for later processing
		try {
			Boolean sendResult = httpChannel.sendCoToSaveProfile(userCO);
			log.debug("Sending profile info to queue resulted in result : {} ", sendResult);
		} catch (Exception e) {
			log.error("Error in push user profile details to queue. Will try profile processing again." + e);
			UserCO userCO1 = mongoOperations2.insert(userCO);
		}
		return modelMapper.map(user, UserDTO.class);
	}

	private String getUserAuthoritesStr(UserCO userCO) {
		StringBuilder authStr = new StringBuilder();
		com.src.oauth2.enums.UserAuthority userAuthority;
		if (Objects.isNull(userCO.getUserRole())) {
			userAuthority = com.src.oauth2.enums.UserAuthority.USER;
			authStr.append(userAuthority.getDescription());
		} else {
			List<Integer> authoritiesIds = (Arrays.stream(userCO.getUserRole().split(",")).map(Integer::valueOf)).collect(Collectors.toList());
			for (Integer authId : authoritiesIds) {
				authStr.append(com.src.oauth2.enums.UserAuthority.valueOf(authId).getDescription()).append(",");
			}
		}
		return authStr.toString();
	}

	@Override
//	@Transactional
	public ResponseEntity<?> loginUser(UserLoginCO userLoginCO) {
		LoginRequest loginRequest = modelMapper.map(userLoginCO, LoginRequest.class);
		return authControllerV1.authenticateUser(loginRequest);
	}
}
