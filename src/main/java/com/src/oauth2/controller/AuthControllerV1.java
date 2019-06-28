package com.src.oauth2.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Priya on 11/3/2017
 */
@RestController
@RequestMapping("/auth/v1")
@Slf4j
public class AuthControllerV1 {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String userProfile(HttpServletRequest request) throws Exception {
		log.debug("Requesting User Profile page Ip: " + request.getRemoteAddr());
		return "userProfile";
	}


}
