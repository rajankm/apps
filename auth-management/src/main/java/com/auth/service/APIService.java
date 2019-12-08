package com.auth.service;

import java.io.Serializable;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

@Component
public interface APIService {

	public default <T extends Serializable> HttpEntity<T> getForEntity(Map<String,?> uriVariables, Class<T> clazz) {
		HttpEntity<?> responseEntity = ResponseEntity.EMPTY;
		
		RestTemplate restTemplate = new RestTemplate();
		try{
			responseEntity = restTemplate.getForEntity(getEndPointURL(), clazz, uriVariables);
		}catch(HttpClientErrorException httpEx){
			httpEx.printStackTrace();
		}catch(Exception ex) {
			if(ex instanceof ResourceAccessException) {
				System.out.println(ex.getMessage());
			}
			ex.printStackTrace();
		}
		return (HttpEntity<T>)responseEntity;
	}
	
	public String getEndPointURL();
		
}
