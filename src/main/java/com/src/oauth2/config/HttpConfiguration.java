package com.src.oauth2.config;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

/**
 * Configuration class for generating {@link RestTemplate} with default
 * parameters
 *
 * @author goutam.mandal
 */
@Configuration
public class HttpConfiguration {


	@Getter
	@Value("${bus.service.url}")
	protected String busServiceUrl;
	@Getter
	@Value("${service.connectionTimeout}")
	protected Integer connectionTimeout;

	@Getter
	@Value("${service.socketTimeout}")
	protected Integer socketTimeout;

	@Getter
	@Value("${service.connectionRequestTimeout}")
	protected Integer connectionRequestTimeout;

	@Getter
	@Value("${service.maxTotalOpenConnections}")
	protected Integer maxTotalOpenConnections;

	@Getter
	@Value("${service.maxConcurrentConnectionsPerRoute}")
	protected Integer maxConcurrentConnectionsPerRoute;


	private PoolingHttpClientConnectionManager poolingHttpClientConnectionManager() {

		PoolingHttpClientConnectionManager manager = new PoolingHttpClientConnectionManager();
		manager.setMaxTotal(maxTotalOpenConnections);
		manager.setDefaultMaxPerRoute(maxConcurrentConnectionsPerRoute);

		return manager;
	}

	private RequestConfig defaultRequestConfig() {

		/**
		 * Connection Timeout – time to establish the connection with the remote host
		 * Socket Timeout – time waiting for data after the connection was established;
		 * maximum time of inactivity between two data packets Connection Request
		 * Timeout – time to wait for a connection from the connection manager/pool
		 */
		// TODO
		return RequestConfig.custom()
				.setConnectTimeout(connectionTimeout)
				.setSocketTimeout(socketTimeout)
				.setConnectionRequestTimeout(connectionRequestTimeout)
				.build();
	}

	private List<Header> defaultHeaders() {

		List<Header> headers = new ArrayList<>();

		headers.add(new BasicHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON));

		return headers;
	}

	private HttpRequestInterceptor getRequestIdInterceptor() {

		return (request, context) -> {
			String requestId = MDC.get("requestId");
			if (!StringUtils.isEmpty(requestId)) {
				request.setHeader(new BasicHeader("JS-Request-Id", requestId));
			}

		};
	}

	private CloseableHttpClient httpClient() {

		return HttpClientBuilder.create().setConnectionManager(poolingHttpClientConnectionManager())
				.setDefaultRequestConfig(defaultRequestConfig()).setDefaultHeaders(defaultHeaders()) // Setting them
				// during req
				// creation adds
				// them as comma
				// separated
				// values
				.addInterceptorLast(getRequestIdInterceptor()) // Setting them during req creation does not add/override
				.build();
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate(new HttpComponentsClientHttpRequestFactory(httpClient()));
	}


}