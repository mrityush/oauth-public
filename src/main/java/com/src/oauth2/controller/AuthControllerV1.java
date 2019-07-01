package com.src.oauth2.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Priya on 11/3/2017
 */
@Controller
//@RestController
@RequestMapping("/auth/v1")
@Slf4j
public class AuthControllerV1 {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String userProfile(@RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient authorizedClient,
							  @AuthenticationPrincipal OAuth2User oauth2User, HttpServletRequest request) throws Exception {
		log.debug("Requesting User Profile page Ip: " + request.getRemoteAddr());
		return "userProfile";
	}


}
