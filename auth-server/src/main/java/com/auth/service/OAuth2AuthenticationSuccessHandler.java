package com.auth.service;

import static com.auth.service.HttpCookieOAuth2AuthorizationRequestRepository.OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME;
import static com.auth.service.HttpCookieOAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME;

import java.io.IOException;
import java.net.URI;
import java.util.Enumeration;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.connector.CoyoteReader;
import org.apache.catalina.connector.Request;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.client.web.HttpSessionOAuth2AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import com.auth.exception.BadRequestException;
import com.auth.util.CookieUtils;

@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
	private static final Log logger = LogFactory.getLog(OAuth2AuthenticationSuccessHandler.class);
	@Autowired
	OAuth2ClientContext oauth2ClientContext;

	// private TokenProvider tokenProvider;

	// private AppProperties appProperties;
	@Autowired
	private HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;
	@Autowired
	private AuthorizationRequestRepository<OAuth2AuthorizationRequest> authorizationRequestRepository;
	/*
	 * @Autowired OAuth2AuthenticationSuccessHandler(TokenProvider tokenProvider,
	 * AppProperties appProperties, HttpCookieOAuth2AuthorizationRequestRepository
	 * httpCookieOAuth2AuthorizationRequestRepository) { this.tokenProvider =
	 * tokenProvider; this.appProperties = appProperties;
	 * this.httpCookieOAuth2AuthorizationRequestRepository =
	 * httpCookieOAuth2AuthorizationRequestRepository; }
	 */
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		logger.debug("Authentication success.");
		String currentIri = oauth2ClientContext.getAccessTokenRequest().getCurrentUri();		
		
		String targetUrl = determineTargetUrl(request, response, authentication);

		if (response.isCommitted()) {
			logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
			return;
		}

		//clearAuthenticationAttributes(request, response);
		getRedirectStrategy().sendRedirect(request, response, targetUrl);
	}

	@Override
	protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		return super.determineTargetUrl(request, response, authentication);
	}

	/*	protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) {
		Optional<String> redirectUri = CookieUtils.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
				.map(Cookie::getValue);

		if (redirectUri.isPresent() && !isAuthorizedRedirectUri(redirectUri.get())) {
			throw new BadRequestException(
					"Sorry! We've got an Unauthorized Redirect URI and can't proceed with the authentication");
		}

		String targetUrl = redirectUri.orElse(getDefaultTargetUrl());

		String token = "testTokenfromOAuth2AuthenticationSuccessHandler";// tokenProvider.createToken(authentication);
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("token", token);
		Optional<Cookie> cookies = CookieUtils.getCookie(request, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME);
		String authUserStr = null;
		if (cookies.isPresent()) {
			authUserStr = cookies.get().getValue();
			params.add("authuser", authUserStr);
		}
		logger.debug("Redirecting after authenticationSuccesss: " + targetUrl);
		return UriComponentsBuilder.fromUriString(targetUrl).queryParams(params).build().toUriString();
	}
*/
	protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
		super.clearAuthenticationAttributes(request);
		httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
	}

	private boolean isAuthorizedRedirectUri(String uri) {
		URI clientRedirectUri = URI.create(uri);

		/*
		 * return appProperties.getOauth2().getAuthorizedRedirectUris() .stream()
		 * .anyMatch(authorizedRedirectUri -> { // Only validate host and port. Let the
		 * clients use different paths if they want to URI authorizedURI =
		 * URI.create(authorizedRedirectUri);
		 * if(authorizedURI.getHost().equalsIgnoreCase(clientRedirectUri.getHost()) &&
		 * authorizedURI.getPort() == clientRedirectUri.getPort()) { return true; }
		 * return false; });
		 */return true;

	}
}