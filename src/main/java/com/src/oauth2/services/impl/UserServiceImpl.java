package com.src.oauth2.services.impl;

import com.src.oauth2.command.UserCO;
import com.src.oauth2.command.UserLoginCO;
import com.src.oauth2.domain.RegisteredUser;
import com.src.oauth2.domain.User;
import com.src.oauth2.domain.UserAuthority;
import com.src.oauth2.services.interfaces.UserService;
import org.springframework.stereotype.Service;

/**
 * oauth2
 * Mrityunjaya@infoedge
 * 27/6/19 4:21 PM
 */
@Service
public class UserServiceImpl implements UserService {
	@Override
	public void sendWelcomeEmail(UserAuthority userAuthority) {

	}

	@Override
	public String getReferralCodeForUser(RegisteredUser user) {
		return null;
	}

	@Override
	public User registerUser(UserCO userCO) {
		return null;
	}

	@Override
	public User loginUser(UserLoginCO userLoginCO) {
		return null;
	}
}
