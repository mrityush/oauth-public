//package com.src.oauth2.config.oauth;
//
//import com.src.oauth2.domain.RegisteredUser;
//import com.src.oauth2.domain.UserAuthority;
//import com.src.oauth2.enums.LoginSignupType;
//import lombok.Data;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//
//import java.util.Map;
//
//
//@ConfigurationProperties(prefix = "security.oauth2.entity")
//@Data
//public class OAuth2UserProperties {
//
//	private String idKey;
//	private String emailKey;
//	private String nameKey;
//	private String firstNameKey;
//	private String lastNameKey;
//	private String profileKey;
//	private String imageKey;
//	private String localeKey;
//	private String isAccountVerifiedKey;
//
//	public UserAuthority buildUser(Map<String, Object> map, LoginSignupType loginSignupType) {
//		RegisteredUser user = new RegisteredUser();
//		String email = (String) map.get(this.getEmailKey());
//		user.setEmail(email);
//		user.setFirstName((String) map.get(this.getFirstNameKey()));
//		user.setLastName((String) map.get(this.getLastNameKey()));
//		user.setPhotoUrl((String) map.get(this.getImageKey()));
//		user.setLoginSignupType(loginSignupType);
//		user.setUserSocialId((String) map.get(this.getIdKey()));
//		user.setEnabled(true);
//		user.setSubscribedToEmail(true);
//
//		return new UserAuthority(user, "ROLE_USER");
//	}
//}
