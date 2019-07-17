package com.src.oauth2.datachannels;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.src.oauth2.command.UserCO;
import com.src.oauth2.config.HttpConfiguration;
import com.src.oauth2.dto.QueueAndMessageDto;
import com.src.oauth2.dto.RequestBuilderDTO;
import com.src.oauth2.utils.ApiRequestBuilder;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Slf4j
@Service
public class HttpChannel {

	@Autowired
	protected HttpConfiguration httpConfiguration;
	@Autowired
	private ApiRequestBuilder apiRequestBuilder;
	@Autowired
	protected RestTemplate restTemplate;
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private ModelMapper modelMapper;
	@Value("${profile.queue.name}")
	private String profileQueue;

	public Boolean sendCoToSaveProfile(UserCO userCO) throws JsonProcessingException {
//		UriComponentsBuilder builder = UriComponentsBuilder
//				.fromHttpUrl(httpConfiguration.getBusServiceUrl() + "/v1/bus");
		RequestBuilderDTO requestBuilderDTO = new RequestBuilderDTO();
		requestBuilderDTO.setUrl("http://" + httpConfiguration.getBusServiceUrl() + "/v1/bus");
		requestBuilderDTO.setHttpMethod(HttpMethod.POST);
		QueueAndMessageDto queueAndMessageDto = new QueueAndMessageDto();
		queueAndMessageDto.setTopic(profileQueue);
		queueAndMessageDto.setMessage(userCO);
		requestBuilderDTO.setRequestBody(objectMapper.convertValue(queueAndMessageDto, Map.class));
		ResponseEntity<Map> response = apiRequestBuilder.executeRequest(requestBuilderDTO, Map.class);
		return response != null;
	}
}
