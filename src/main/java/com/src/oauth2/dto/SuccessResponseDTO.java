package com.src.oauth2.dto;

import org.apache.http.HttpStatus;

/**
 * Created by mj on 1/6/16.
 */
public class SuccessResponseDTO extends RequestResponseDTO {
	public SuccessResponseDTO() {
		super("Success in Action", HttpStatus.SC_OK, true);
	}

	public SuccessResponseDTO(String message) {
		super(message, HttpStatus.SC_OK, true);
	}
}
