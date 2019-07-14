package com.src.oauth2.dto;

import org.apache.http.HttpStatus;

/**
 * Created by mj on 1/6/16.
 */
public class ErrorResponseDTO extends RequestResponseDTO {
	public ErrorResponseDTO() {
		super("Failure in Action", HttpStatus.SC_INTERNAL_SERVER_ERROR, true);
	}

	public ErrorResponseDTO(String message) {
		super(message, HttpStatus.SC_INTERNAL_SERVER_ERROR, true);
	}
}
