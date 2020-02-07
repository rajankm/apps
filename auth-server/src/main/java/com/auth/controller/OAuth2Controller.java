package com.auth.controller;

import java.util.Collections;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login/oauth2")
public class OAuth2Controller extends GenericController {
	
	@GetMapping("/callback/{provider}")
	public Map<String, Object> ssoUser(@PathVariable String provider){
		logger.debug("User Authorized: Provider: "+provider);
		/*
		 * logger.debug(userPrincipal.getAttributes()+", "+userPrincipal.getUsername()
		 * +", "+userPrincipal.getPassword());
		 */
		return Collections.singletonMap("name",provider);
	}
	
}
