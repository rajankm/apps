package com.app.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@WebFilter(urlPatterns = { "/api" })
public class RequestFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		String uri = "http://localhost:8082/api/validate";

		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		try {
			headers.add(HttpHeaders.AUTHORIZATION,
					httpRequest.getSession().getAttribute(HttpHeaders.AUTHORIZATION).toString());
			HttpEntity<?> httpEntity = new HttpEntity<>(headers);
			ResponseEntity<String> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, httpEntity,
					String.class);
			if (responseEntity.getStatusCode() == HttpStatus.OK) {
				filterChain.doFilter(request, response);
			} else {
				throw new Exception("Bad response: " + responseEntity.getStatusCode());
			}
		} catch (Exception e) {
			e.printStackTrace();
			httpResponse.sendRedirect(httpRequest.getContextPath() + "/user/form");
		}
	}

}
