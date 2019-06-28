package com.src.oauth2.services.interfaces;

import com.src.oauth2.domain.RegisteredUser;
import com.src.oauth2.domain.UserAuthority;

/**
 * Created by Priya on 13/3/17.
 */
public interface UserService {

	void sendWelcomeEmail(UserAuthority userAuthority);

	String getReferralCodeForUser(RegisteredUser user);
}
