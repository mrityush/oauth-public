package com.src.oauth2.dto;

import lombok.Data;


@Data
public class QueueAndMessageDto implements BaseDTO{
	private String topic;
	private String exchange;
	private String topicKey;
	private Object message;
}
