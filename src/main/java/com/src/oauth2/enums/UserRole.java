package com.src.oauth2.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mj on 24/5/16.
 */
public enum UserRole {
	ADMIN(0, "ADMIN_ROLE"),
	SERVICE_PROVIDER(1, "SERVICE_PROVIDER_ROLE"),
	GOODS_PROVIDER(2, "GOODS_PROVIDER_ROLE"),
	USER(3, "USER_ROLE");

	Integer authorityCode;
	String discription;

	public Integer getAuthorityCode() {
		return authorityCode;
	}

	public String getDiscription() {
		return discription;
	}

	UserRole(int value, String discription) {
		this.authorityCode = value;
		this.discription = discription;
	}

	static Map<Integer, UserRole> authoritiesMap = new HashMap<>();

	static {
		for (UserRole userRole : UserRole.values()) {
			authoritiesMap.put(userRole.getAuthorityCode(), userRole);
		}
	}

	public static UserRole valueOf(Integer enumValue) {
		return authoritiesMap.get(enumValue);
	}
}
