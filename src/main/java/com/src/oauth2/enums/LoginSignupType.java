package com.src.oauth2.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * oauth2
 * Mrityunjaya@infoedge
 * 26/6/19 4:41 PM
 */
@Getter
public enum LoginSignupType {
	Google(0, "google"),
	Twitter(1, "twitter"),
	Self(2, "self"),
	Facebook(3, "facebook");

	Integer value;
	String description;

	LoginSignupType(int value, String description) {
		this.value = value;
		this.description = description;
	}

	static Map<Integer, LoginSignupType> authoritiesMap = new HashMap<>();

	static {
		for (LoginSignupType generalStatus : LoginSignupType.values()) {
			authoritiesMap.put(generalStatus.getValue(), generalStatus);
		}
	}

	public static LoginSignupType valueOf(Integer enumValue) {
		return authoritiesMap.get(enumValue);
	}
}
