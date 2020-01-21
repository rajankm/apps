package com.app.entity.dto;

import java.net.URL;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONResponse {
	private HttpStatus status = HttpStatus.NOT_FOUND;
	private Object value;
	private URL redirectURL;
	public HttpStatus getStatus() {
		return status;
	}
	public void setStatus(HttpStatus status) {
		this.status = status;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	public URL getRedirectURL() {
		return redirectURL;
	}
	public void setRedirectURL(URL redirectURL) {
		this.redirectURL = redirectURL;
	}
	@Override
	public String toString() {
	    try {
	        return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(this);
	    } catch (JsonProcessingException e) {
	        e.printStackTrace();
	    }
	    return null;
	}
}
