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
public enum UserAuthority {
	USER(0, "ROLE_USER"),
	ADMIN(1, "ROLE_ADMIN");

	Integer value;
	String description;

	UserAuthority(int value, String description) {
		this.value = value;
		this.description = description;
	}

	static Map<Integer, UserAuthority> authoritiesMap = new HashMap<>();

	static {
		for (UserAuthority generalStatus : UserAuthority.values()) {
			authoritiesMap.put(generalStatus.getValue(), generalStatus);
		}
	}

	public static UserAuthority valueOf(Integer enumValue) {
		return authoritiesMap.get(enumValue);
	}
}
