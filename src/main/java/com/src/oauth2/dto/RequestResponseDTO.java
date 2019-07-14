package com.src.oauth2.dto;

import lombok.Data;

/**
 * Created by mj on 1/6/16.
 */
@Data
public class RequestResponseDTO {
	private String message;
	private Integer status;
	private boolean isActionDone;
	private Object data;
	private String nextCursor;

	public RequestResponseDTO() {
		status = 200;
		message = "dummy Message";
		isActionDone = false;
	}

	public RequestResponseDTO(boolean b) {
		status = 200;
		message = "dummy Message";
		isActionDone = b;
	}

	public RequestResponseDTO(String message, int status, boolean actionDone) {
		this.status = status;
		this.message = message;
		isActionDone = actionDone;
	}

}
