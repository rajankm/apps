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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@WebFilter(urlPatterns = { "/api/**" })
public class RequestFilter implements Filter {
	protected final Log logger = LogFactory.getLog(getClass());

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		String uri = "http://localhost:8081/authenticate/api";

		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		logger.info("Authenticating jwtToken for request.");
		try {
			headers.add(HttpHeaders.AUTHORIZATION, httpRequest.getParameter("jwtToken"));
			HttpEntity<?> httpEntity = new HttpEntity<>(headers);
			ResponseEntity<String> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, httpEntity,
					String.class);
			if (responseEntity.getStatusCode() == HttpStatus.OK) {
				logger.info("Authentication of jwtToken passed.");
				filterChain.doFilter(request, response);
			} else {
				throw new Exception("Bad response: " + responseEntity.getStatusCode());
			}
		} catch (Exception e) {
			logger.warn("Authentication of jwtToken failed.");
			e.printStackTrace();
			httpResponse.sendRedirect(httpRequest.getContextPath() + "/user/login?srcPage=");
		}
	}

}
