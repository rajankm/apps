package com.app.controller;

import java.util.Map;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(path = { "/api" })
public class APICallController {
	@GetMapping(path = { "/{targetUrl}" })
	public String invokeTargetUri(@PathVariable(required = true) String targetUrl,
			@RequestBody Map<String, String> uriVariables) throws Exception {
		try {
			targetUrl = "http://54.187.253.221:8083/api/pincode/1/122001";

			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<String> responseEntity = restTemplate.exchange(targetUrl, HttpMethod.GET, null, String.class, uriVariables);
			return responseEntity.getBody();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}
}
