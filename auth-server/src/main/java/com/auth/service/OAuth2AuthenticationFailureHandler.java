package com.auth.service;

import static com.auth.service.HttpCookieOAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.auth.util.CookieUtils;

@Component
public class OAuth2AuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
	private static final Log logger = LogFactory.getLog(OAuth2AuthenticationFailureHandler.class);
	/*
	 * @Autowired HttpCookieOAuth2AuthorizationRequestRepository
	 * httpCookieOAuth2AuthorizationRequestRepository;
	 */

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		logger.debug("login failure.");
		String targetUrl = CookieUtils.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME).map(Cookie::getValue)
				.orElse(("/"));

		targetUrl = UriComponentsBuilder.fromUriString(targetUrl).queryParam("error", exception.getLocalizedMessage())
				.build().toUriString();

		//httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
		logger.debug("Redirecting to targetUrl: "+targetUrl);
		getRedirectStrategy().sendRedirect(request, response, targetUrl);
	}
}