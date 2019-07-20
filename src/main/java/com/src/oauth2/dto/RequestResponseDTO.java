package com.src.oauth2.dto;

import lombok.Data;

/**
 * Created by mj on 1/6/16.
 */
@Data
public class RequestResponseDTO {
	private String message;
	private Integer status;
	private Long timeStamp;
	private Object data;
	private String nextCursor;

	public RequestResponseDTO() {
		status = 200;
		message = "dummy Message";
		timeStamp = System.currentTimeMillis();
	}

	public RequestResponseDTO(String message) {
		status = 200;
		this.message = message;
		timeStamp = System.currentTimeMillis();
	}

	public RequestResponseDTO(String message, int status) {
		status = status;
		this.message = message;
		timeStamp = System.currentTimeMillis();
	}



}
