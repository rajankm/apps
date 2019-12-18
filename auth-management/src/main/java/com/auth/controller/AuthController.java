package com.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth.model.JWTRequest;
import com.auth.model.dto.UserDto;
import com.auth.service.AuthUserDetailsService;

@RestController
@RequestMapping(path = { "/authenticate" })
public class AuthController extends GenericAuthController {

	@Autowired
	private AuthUserDetailsService authUserDetails;

	@PostMapping("/user")
	public ResponseEntity<?> createUserAuthToken(@RequestBody JWTRequest jwtRequest) throws Exception {
		Authentication authentication = authenticate(jwtRequest.getUsername(), jwtRequest.getPassword());
		String username = authentication.getName();
		ResponseEntity.BodyBuilder responseEntity = ResponseEntity.status(HttpStatus.OK);
		String jwtToken = jwtTokenUtil.generateToken(username);

		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.AUTHORIZATION, jwtToken);
		responseEntity = responseEntity.headers(headers);
		final UserDto user = authUserDetails.getUserDetails(jwtRequest.getUsername());
		return responseEntity.body(user);

	}

}
