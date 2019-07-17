package com.src.oauth2.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mj on 24/5/16.
 */
public enum LanguageChoice {
	HINDI(0, "Hindi"),
	ENGLISH(1, "English");

	Integer value;
	String description;

	public Integer getValue() {
		return value;
	}

	public String getDescription() {
		return description;
	}

	LanguageChoice(int value, String description) {
		this.value = value;
		this.description = description;
	}

	static Map<Integer, LanguageChoice> authoritiesMap = new HashMap<>();

	static {
		for (LanguageChoice generalStatus : LanguageChoice.values()) {
			authoritiesMap.put(generalStatus.getValue(), generalStatus);
		}
	}

	public static LanguageChoice valueOf(Integer enumValue) {
		return authoritiesMap.get(enumValue);
	}
}
