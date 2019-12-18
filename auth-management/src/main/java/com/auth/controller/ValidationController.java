package com.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth.model.JWTRequest;

@RestController
@RequestMapping(path = { "/api" })
public class ValidationController extends GenericAuthController {

	@GetMapping("/validate")
	public ResponseEntity<?> validateToken(@RequestBody JWTRequest jwtRequest) throws Exception {
		ResponseEntity.BodyBuilder responseEntity = ResponseEntity.status(HttpStatus.OK);
		return responseEntity.build();
	}

}
