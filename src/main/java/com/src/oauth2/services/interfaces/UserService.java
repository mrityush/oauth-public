package com.src.oauth2.services.interfaces;

import com.src.oauth2.command.UserCO;
import com.src.oauth2.command.UserLoginCO;
import com.src.oauth2.domain.RegisteredUser;
import com.src.oauth2.domain.UserAuthority;
import com.src.oauth2.dto.UserDTO;
import org.springframework.http.ResponseEntity;

/**
 * Created by Priya on 13/3/17.
 */
public interface UserService {

	void sendWelcomeEmail(UserAuthority userAuthority);

	String getReferralCodeForUser(RegisteredUser user);

	UserDTO registerUser(UserCO userCO);

	ResponseEntity<?> loginUser(UserLoginCO userLoginCO);
}
