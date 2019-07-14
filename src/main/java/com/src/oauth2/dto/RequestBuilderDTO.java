package com.src.oauth2.dto;

import com.src.oauth2.utils.ApiRequestUtil;
import lombok.Data;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import java.util.Map;

/**
 * Created by intelligrape on 25/2/16.
 */
@Data
public class RequestBuilderDTO {
	private HttpHeaders httpHeaders = ApiRequestUtil.getHttpHeaders();
	private HttpMethod httpMethod = HttpMethod.GET;
	private Map<String, Object> requestBody = ApiRequestUtil.getRequestBody();
	private String url;
}
