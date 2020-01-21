package com.app.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(path = { "/api" })
public class APICallController extends GenericController {

	@Autowired
	protected RestTemplate restTemplate;

	@GetMapping(path = { "/pincode" })
	public String invokeTargetUri(@RequestParam Map<String, String> uriVariables, HttpServletRequest request)
			throws Exception {
		try {
			String isCallable = request.getHeader(HttpHeaders.AUTHORIZATION);
			if (Boolean.valueOf(isCallable)) {
				String targetUri = getPropValue("url.service.pincode");
				String valuefor = uriVariables.get("valuefor");
				String pincode = uriVariables.get("pincode");
				String targetUrl = targetUri + "/" + valuefor + "/" + pincode;
				logger.info("Calling api: " + targetUrl);
				ResponseEntity<String> responseEntity = restTemplate.exchange(targetUrl, HttpMethod.GET, null,
						String.class, uriVariables);
				logger.info("Response reveived: " + responseEntity);
				return responseEntity.getBody();

			} else {
				logger.info("Bhag Sale");
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
