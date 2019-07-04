package com.src.oauth2.services;

import com.src.oauth2.domain.RegisteredUser;
import com.src.oauth2.domain.UserAuthority;
import com.src.oauth2.repositories.RegisteredUserRepository;
import com.src.oauth2.repositories.UserAuthorityRepository;
import com.src.oauth2.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Created by mj on 27/06/19
 */
@Service(value = "socialService")
public class SocialService {

	@Autowired
	private RegisteredUserRepository registeredUserRepository;

	@Autowired
	private UserAuthorityRepository userAuthorityRepository;

	@Autowired
	private UserService userService;

	@Transactional
	public void save(UserAuthority userAuthority) {
		RegisteredUser registeredUser = userAuthority.getUser();
		if (registeredUserRepository.findByEmail(registeredUser.getEmail()).size() == 0) {
			userAuthorityRepository.save(userAuthority);
			userService.sendWelcomeEmail(userAuthority);
		}
	}

	public String getUserFirstName(UserAuthority userAuthority, String replaceableFirstName) {
		RegisteredUser registeredUser = userAuthority.getUser();
		if (registeredUserRepository.findByEmail(registeredUser.getEmail()).size() == 0) {
			return replaceableFirstName;
		} else {
			return registeredUser.getFirstName();
		}
	}

	public String getUserImageKey(UserAuthority userAuthority, String replaceabeImageKey) {
		RegisteredUser registeredUser = userAuthority.getUser();
		if (registeredUserRepository.findByEmail(registeredUser.getEmail()).size() == 0) {
			return replaceabeImageKey;
		} else {
			return registeredUserRepository.findByEmail(registeredUser.getEmail()).get(0).getPhotoUrl();
		}
	}

	public String getReferralCodeForUser(RegisteredUser user) {
		return userService.getReferralCodeForUser(user);
	}
}
